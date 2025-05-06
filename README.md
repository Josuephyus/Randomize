# Biography

This project was made for DangotheSeaOtter as a replacement for a website based randomizer, but I'll take any requests and bug reports.

Because it is made with Java, as long as Java is installed, it should work.

# How to Use?

Required:
- Java (latest)
- The following items in the same directory
  - thisList.txt
  - icons (folder)

Any champion listed in "thisList.txt" will be used in the randomizer.

# thisList Structure

{name}, {title / description}

All spaces, periods, and apostrophes are removed when searching for the icon of {name}. It only accepts ".pngs" (as of now).

# Current Features

Pressing [ Space ] rolls for a random champion.

Pressing a letter searches for the first champion that starts with that letter

The League of Legends font can be downloaded for additional immersion.

The title is not necessary and can be omitted.

# Caution
1. There is a minimum of 10 entries (can be duplicates)
2. Rolling multiple times (while possible) can cause some unintended behavior
3. An 'endline' character is required at the end of a page (essentially the last line has to be empty).
4. 'thisList.txt' must be 'LF' (not 'CRLF')


# TODO List
- [ ] Pressing Backspace removes a character from the list
- [ ] Create a minimum of one character instead of 10
- [ ] Crash Gracefully when 'thisList.txt' or 'icons' do not exists
- [ ] Generate a list from the current use
- [ ] Pressing enter to refresh the list based on thisList.txt
- [ ] Pressing Ctrl-Z will undo the removal of a character
- [ ] Show a 'None.png' when no '{name}.png' is found
- [ ] Allow Arrow Keys for quick navigation
- [ ] Proper Resizing