package top.yukonga.miuix.docs.icongen

import java.io.File

/** Simple CLI entry that converts Compose vector icon definitions to SVG files. */
fun main(args: Array<String>) {
    // Simple linear parse: --key value or --flag (value true)
    val map = mutableMapOf<String, String>()
    var i = 0
    while (i < args.size) {
        val a = args[i]
        if (a.startsWith("--")) {
            val key = a
            val next = args.getOrNull(i + 1)
            if (next != null && !next.startsWith("--")) {
                map[key] = next; i += 2
            } else {
                map[key] = "true"; i += 1
            }
        } else i++
    }
    val srcDir = map["--src"] ?: error("--src <dir> required")
    val outDir = map["--out"] ?: error("--out <dir> required")
    val light = map["--light"] ?: "#000000"
    val dark = map["--dark"] ?: "#FFFFFF"
    val preserveColors = map["--preserve-colors"]?.equals("true", true) == true

    val src = File(srcDir)
    val dest = File(outDir)
    if (!src.isDirectory) error("Source directory not found: $src")
    if (!dest.exists()) dest.mkdirs()

    val ktFiles = src.walkTopDown().filter { it.isFile && it.extension == "kt" && it.name != "MiuixIcon.kt" }
    var count = 0
    ktFiles.forEach { file ->
        val content = file.readText()
        val builderRegex = Regex("ImageVector\\.Builder\\(\\s*\\\"([^\\\"]+)\\\"\\s*,\\s*([0-9.]+)f?\\.dp\\s*,\\s*([0-9.]+)f?\\.dp\\s*,\\s*([0-9.]+)f\\s*,\\s*([0-9.]+)f")
        val builderMatch = builderRegex.find(content)
        val iconName = builderMatch?.groupValues?.getOrNull(1) ?: file.nameWithoutExtension
        val viewportWidth = builderMatch?.groupValues?.getOrNull(4)?.toFloatOrNull() ?: 24f
        val viewportHeight = builderMatch?.groupValues?.getOrNull(5)?.toFloatOrNull() ?: 24f
        val pathBlocks = Regex("path\\((.*?)\\) *\\{(.*?)\\}", setOf(RegexOption.DOT_MATCHES_ALL)).findAll(content)
        data class SvgPath(
            val d: String,
            val evenOdd: Boolean,
            val fillAlpha: Float?,
            val fill: String?,
            val stroke: String?,
            val strokeWidth: String?,
            val strokeAlpha: Float?,
            val strokeLineCap: String?,
            val strokeLineJoin: String?,
            val strokeMiterLimit: String?
        )
        val paths = mutableListOf<SvgPath>()
        pathBlocks.forEach { m ->
            val header = m.groupValues[1]
            val body = m.groupValues[2]
            val evenOdd = header.contains("EvenOdd") || header.contains("PathFillType.EvenOdd")
            val fillAlpha = Regex("fillAlpha *= *(\\d*\\.?\\d+)f").find(header)?.groupValues?.getOrNull(1)?.toFloatOrNull()
            fun parseColor(kind: String): String? {
                val regex = Regex("$kind *= *SolidColor\\(Color\\.([A-Za-z]+)\\)")
                val named = regex.find(header)?.groupValues?.getOrNull(1)
                if (named != null) return when (named) { "White" -> "#FFFFFF"; "Black" -> "#000000"; "Transparent" -> "none"; else -> null }
                val hexRegex = Regex("$kind *= *SolidColor\\(Color\\((0x[0-9A-Fa-f]{8})\\)\\)")
                val hexMatch = hexRegex.find(header)?.groupValues?.getOrNull(1)
                if (hexMatch != null) {
                    val value = hexMatch.removePrefix("0x")
                    val a = value.substring(0,2); val r = value.substring(2,4); val g = value.substring(4,6); val b = value.substring(6,8)
                    return if (a.equals("FF", true)) "#$r$g$b" else "#$r$g$b$a"
                }
                return null
            }
            val fillColor = parseColor("fill")
            val strokeColor = parseColor("stroke")
            val strokeAlpha = Regex("strokeAlpha *= *(\\d*\\.?\\d+)f").find(header)?.groupValues?.getOrNull(1)?.toFloatOrNull()
            val strokeWidth = Regex("strokeLineWidth *= *(\\d*\\.?\\d+)f").find(header)?.groupValues?.getOrNull(1)
            val strokeLineCap = Regex("strokeLineCap *= *([A-Za-z.]+)").find(header)?.groupValues?.getOrNull(1)?.substringAfterLast('.')
            val strokeLineJoin = Regex("strokeLineJoin *= *([A-Za-z.]+)").find(header)?.groupValues?.getOrNull(1)?.substringAfterLast('.')
            val strokeMiterLimit = Regex("strokeLineMiter *= *(\\d*\\.?\\d+)f").find(header)?.groupValues?.getOrNull(1)
            val sb = StringBuilder()
            val moveRegex = Regex("moveTo\\((.*?)f, (.*?)f\\)"); val lineToRegex = Regex("lineTo\\((.*?)f, (.*?)f\\)"); val curveToRegex = Regex("curveTo\\((.*?)f, (.*?)f, (.*?)f, (.*?)f, (.*?)f, (.*?)f\\)")
            val moveRelRegex = Regex("moveToRelative\\((.*?)f, (.*?)f\\)"); val lineRelRegex = Regex("lineToRelative\\((.*?)f, (.*?)f\\)"); val curveRelRegex = Regex("curveToRelative\\((.*?)f, (.*?)f, (.*?)f, (.*?)f, (.*?)f, (.*?)f\\)")
            val hLineRegex = Regex("horizontalLineTo\\((.*?)f\\)"); val vLineRegex = Regex("verticalLineTo\\((.*?)f\\)"); val hLineRelRegex = Regex("horizontalLineToRelative\\((.*?)f\\)"); val vLineRelRegex = Regex("verticalLineToRelative\\((.*?)f\\)")
            val quadRegex = Regex("quadTo\\((.*?)f, (.*?)f, (.*?)f, (.*?)f\\)"); val quadRelRegex = Regex("quadToRelative\\((.*?)f, (.*?)f, (.*?)f, (.*?)f\\)")
            val rCurveRegex = Regex("reflectiveCurveTo\\((.*?)f, (.*?)f, (.*?)f, (.*?)f\\)"); val rCurveRelRegex = Regex("reflectiveCurveToRelative\\((.*?)f, (.*?)f, (.*?)f, (.*?)f\\)")
            val rQuadRegex = Regex("reflectiveQuadTo\\((.*?)f, (.*?)f\\)"); val rQuadRelRegex = Regex("reflectiveQuadToRelative\\((.*?)f, (.*?)f\\)"); val closeRegex = Regex("close\\(\\)")
            body.lineSequence().forEach { line ->
                moveRegex.findAll(line).forEach { r -> sb.append("M${r.groupValues[1]} ${r.groupValues[2]} ") }
                lineToRegex.findAll(line).forEach { r -> sb.append("L${r.groupValues[1]} ${r.groupValues[2]} ") }
                curveToRegex.findAll(line).forEach { r -> sb.append("C${r.groupValues[1]} ${r.groupValues[2]} ${r.groupValues[3]} ${r.groupValues[4]} ${r.groupValues[5]} ${r.groupValues[6]} ") }
                moveRelRegex.findAll(line).forEach { r -> sb.append("m${r.groupValues[1]} ${r.groupValues[2]} ") }
                lineRelRegex.findAll(line).forEach { r -> sb.append("l${r.groupValues[1]} ${r.groupValues[2]} ") }
                curveRelRegex.findAll(line).forEach { r -> sb.append("c${r.groupValues[1]} ${r.groupValues[2]} ${r.groupValues[3]} ${r.groupValues[4]} ${r.groupValues[5]} ${r.groupValues[6]} ") }
                hLineRegex.findAll(line).forEach { r -> sb.append("H${r.groupValues[1]} ") }
                vLineRegex.findAll(line).forEach { r -> sb.append("V${r.groupValues[1]} ") }
                hLineRelRegex.findAll(line).forEach { r -> sb.append("h${r.groupValues[1]} ") }
                vLineRelRegex.findAll(line).forEach { r -> sb.append("v${r.groupValues[1]} ") }
                quadRegex.findAll(line).forEach { r -> sb.append("Q${r.groupValues[1]} ${r.groupValues[2]} ${r.groupValues[3]} ${r.groupValues[4]} ") }
                quadRelRegex.findAll(line).forEach { r -> sb.append("q${r.groupValues[1]} ${r.groupValues[2]} ${r.groupValues[3]} ${r.groupValues[4]} ") }
                rCurveRegex.findAll(line).forEach { r -> sb.append("S${r.groupValues[1]} ${r.groupValues[2]} ${r.groupValues[3]} ${r.groupValues[4]} ") }
                rCurveRelRegex.findAll(line).forEach { r -> sb.append("s${r.groupValues[1]} ${r.groupValues[2]} ${r.groupValues[3]} ${r.groupValues[4]} ") }
                rQuadRegex.findAll(line).forEach { r -> sb.append("T${r.groupValues[1]} ${r.groupValues[2]} ") }
                rQuadRelRegex.findAll(line).forEach { r -> sb.append("t${r.groupValues[1]} ${r.groupValues[2]} ") }
                if (closeRegex.containsMatchIn(line)) sb.append("Z ")
            }
            val d = sb.toString().trim()
            if (d.isNotBlank()) paths += SvgPath(d, evenOdd, fillAlpha, fillColor, strokeColor, strokeWidth, strokeAlpha, strokeLineCap, strokeLineJoin, strokeMiterLimit)
        }
        if (paths.isEmpty()) return@forEach
        fun num(v: Float): String = if (v % 1f == 0f) v.toInt().toString() else v.toString().trimEnd('0').trimEnd('.')
        val svg = buildString {
            appendLine("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
            appendLine("<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"${num(viewportWidth)}\" height=\"${num(viewportHeight)}\" viewBox=\"0 0 ${num(viewportWidth)} ${num(viewportHeight)}\" fill=\"none\" stroke=\"none\">")
            appendLine("  <style>\n    :root{color-scheme:light dark;}\n    svg{color:$light;}\n    @media (prefers-color-scheme: dark){svg{color:$dark;}}\n  </style>")
            paths.forEach { sp ->
                val rule = if (sp.evenOdd) " fill-rule=\"evenodd\" clip-rule=\"evenodd\"" else ""
                val alphaAttr = if (sp.fillAlpha != null && sp.fillAlpha < 0.999f) " fill-opacity=\"${"%.3f".format(sp.fillAlpha)}\"" else ""
                val fillValue = if (preserveColors) {
                    when (sp.fill) { null -> "currentColor"; else -> sp.fill }
                } else {
                    when (sp.fill) { null, "#FFFFFF", "#000000" -> "currentColor"; "none" -> "none"; else -> sp.fill }
                }
                val fillAttr = " fill=\"$fillValue\""
                val strokeAttr = sp.stroke?.let { col -> val value = if (!preserveColors && (col == "#FFFFFF" || col == "#000000")) "currentColor" else col; " stroke=\"$value\"" } ?: ""
                val strokeWidthAttr = sp.strokeWidth?.let { " stroke-width=\"$it\"" } ?: ""
                val strokeAlphaAttr = sp.strokeAlpha?.let { if (it < 0.999f) " stroke-opacity=\"${"%.3f".format(it)}\"" else "" } ?: ""
                val lineCapAttr = sp.strokeLineCap?.let { " stroke-linecap=\"${it.lowercase()}\"" } ?: ""
                val lineJoinAttr = sp.strokeLineJoin?.let { " stroke-linejoin=\"${it.lowercase()}\"" } ?: ""
                val miterAttr = sp.strokeMiterLimit?.let { " stroke-miterlimit=\"$it\"" } ?: ""
                appendLine("  <path d=\"${sp.d}\"${fillAttr}${strokeAttr}${strokeWidthAttr}${strokeAlphaAttr}${lineCapAttr}${lineJoinAttr}${miterAttr}${rule}${alphaAttr} />")
            }
            appendLine("</svg>")
        }
        val relativeDir = file.relativeTo(src).parentFile
        val outDir = File(dest, relativeDir.path)
        outDir.mkdirs()
        File(outDir, iconName + ".svg").writeText(svg)
        count++
    }
    println("[iconGen] Generated $count SVG(s) into $dest")
}
