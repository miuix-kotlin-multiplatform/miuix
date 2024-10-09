function hideLoading() {
    document.getElementById('loading').style.display = 'none';
    document.getElementById('composeApplication').style.display = 'block';
}

function jsInt8ArrayToKotlinByteArray(x) {
    const size = x.length;
    const memBuffer = new ArrayBuffer(size);
    const mem8 = new Int8Array(memBuffer);
    mem8.set(x);
    const byteArray = new Uint8Array(memBuffer);
    return byteArray;
}