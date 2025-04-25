# Components

Miuix provides a rich set of UI components that strictly follow Xiaomi HyperOS Design Guidelines. Each component is carefully designed to ensure visual and interactive consistency with the native Xiaomi experience.

## Scaffold Components

| Component                          | Description                   | Common Usage                    |
| ---------------------------------- | ----------------------------- | ------------------------------- |
| [Scaffold](../components/scaffold) | Basic layout for applications | Page structure, content display |

::: warning
The Scaffold component provides a suitable container for cross-platform popup windows. Components such as `SuperDialog`, `SuperDropdown`, `SuperSpinner`, and `ListPopup` are all implemented based on this and therefore need to be wrapped by this component.
:::

## Basic Components

| Component                                                  | Description                                | Common Usage                          |
| ---------------------------------------------------------- | ------------------------------------------ | ------------------------------------- |
| [Surface](../components/surface)                           | Basic container component                  | Content display, background container |
| [TopAppBar](../components/topappbar)                       | Application top navigation bar             | Page title, primary actions           |
| [NavigationBar](../components/navigationbar)               | Bottom navigation component                | Main page switching                   |
| [TabRow](../components/tabrow)                             | Horizontal tab bar                         | Content category browsing             |
| [Card](../components/card)                                 | Container with related information         | Information display, content grouping |
| [BasicComponent](../components/basiccomponent)             | Universal base component                   | Custom component development          |
| [Button](../components/button)                             | Interactive element for triggering actions | Form submission, action confirmation  |
| [Text](../components/text)                                 | Display text content with various styles   | Titles, body text, descriptive text   |
| [SmallTitle](../components/smalltitle)                     | Small title component                      | Auxiliary titles, category labels     |
| [TextField](../components/textfield)                       | Receives user text input                   | Form filling, search boxes            |
| [Switch](../components/switch)                             | Binary state toggle control                | Setting switches, feature enabling    |
| [Checkbox](../components/checkbox)                         | Multiple selection control                 | Multiple choices, terms agreement     |
| [Slider](../components/slider)                             | Sliding control for value adjustment       | Volume control, range selection       |
| [ProgressIndicator](../components/progressindicator)       | Displays operation progress status         | Loading, progress display             |
| [Icon](../components/icon)                                 | Icon display component                     | Icon buttons, status indicators       |
| [FloatingActionButton](../components/floatingactionbutton) | Floating action button                     | Primary actions, quick functions      |
| [FloatingToolbar](../components/floatingtoolbar)           | Floating toolbar                           | Quick actions, information display    |
| [Divider](../components/divider)                           | Content separator                          | Block separation, hierarchy division  |
| [PullToRefresh](../components/pulltorefresh)               | Pull-down refresh operation                | Data update, page refresh             |
| [SearchBar](../components/searchbar)                       | Search input field                         | Content search, quick find            |
| [ColorPicker](../components/colorpicker)                   | Color selection control                    | Theme settings, color selection       |
| [ListPopup](../components/listpopup)                       | List popup window component                | Option selection, feature list        |

## Extended Components

| Component                                    | Description                                | Common Usage                           |
| -------------------------------------------- | ------------------------------------------ | -------------------------------------- |
| [SuperArrow](../components/superarrow)       | Arrow component based on BasicComponent    | Clickable indication, navigation hints |
| [SuperSwitch](../components/superswitch)     | Switch component based on BasicComponent   | Setting switches, feature enabling     |
| [SuperCheckBox](../components/supercheckbox) | Checkbox component based on BasicComponent | Multiple selection, terms agreement    |
| [SuperDropdown](../components/superdropdown) | Dropdown menu based on BasicComponent      | Option selection, feature list         |
| [SuperSpinner](../components/superspinner)   | Advanced menu based on BasicComponent      | Advanced options, feature list         |
| [SuperDialog](../components/superdialog)     | Dialog window based on BasicComponent      | Prompts, action confirmation           |
