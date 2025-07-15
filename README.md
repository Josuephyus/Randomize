# What is "Randomized!"?

It's just to make the act of picking something random prettier.
It's made to run on low end computers and only works when in focus.

# Download

Required: Java (Either Java SE 8 or any JDK after 8)

1. Download the "Default Installation" .zip or .tar
2. Unzip or... untar the "Default Installation"
3. Double click on Randomized.jar

There has been issues where java fails to run Randomized.jar.
If that is the case, use Randomized.bat (Windows) or Randomized.sh (Linux)

# Usage and Modification

1. "open.txt" is what is shown when opened or refreshed.
2. "display_#.html" is the format of each selection

"display.html" has the option for 'arguments' using {#}. "display.html" will take the {#} option (starting from 0) from open.txt.

There are additional Keybinds...
- [ Space ] Roll
- [ {Letter} ] Finds the first {0} starting with that letter
- [ Up Arrow 'or' Down Arrow ] Select Next/Previous
- [ Enter ] Refresh the list from "open.txt"
- [ Backspace ] Removes Selected
- [ Left Arrow ] Undo (for Backspace)
- [ Right Arrow ] Exports the current list as "export.txt"
- [ 1/2/3/4 ] Change to display_#.html

Closing the window will save the list as "close.txt"

# Caution

The text file's must be LF (as of now).

# To Do List :

- [-] Create a minimum of one character instead of 10
- [-] Generate a list from the current use
- [-] Pressing enter to refresh the list based on open.txt
- [-] Show a 'None.png' when no '{name}.png' is found
- [-] Allow Arrow Keys for quick navigation
- [-] Crash Gracefully when 'open.txt' or 'Icons' do not exists
- [-] Pressing Backspace removes a character from the list
- [-] Pressing [ Left Arrow ] will undo the removal of a character
- [-] Resize options
- [ ] Allow for less characters when deleting