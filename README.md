# Biography

This project was made for DangotheSeaOtter as a replacement for a website based randomizer, but I'll take any requests and bug reports.

# How to Use?

Required: Java (Either JDK 24)

1. Extract 'RANDOM_CHAMPION'
2. Run Execute.cmd (Window) or Execute.sh (Linux).

Any champion listed in "open.txt" will be used in the randomizer.

The 'Execute' files just run the command 'java -jar RandomChampion.jar' so feel free to skip them if you can. I need them because... I don't know actually...

# 'open.txt' Structure

{name}, {title / description}

All spaces, periods, and apostrophes are removed from {name} when searching for the icon. It only accepts ".pngs" (as of now).

# Current Features

1. Pressing [ Space ] rolls for a random champion.
2. Pressing a letter searches for the first champion that starts with that letter
3. Pressing [ Up Arrow ] or [ Down Arrow ] will move up or down on the champion list
4. Pressing [ Enter ] will refresh the list (so you can change without closing)
5. Pressing [ Backspace ] removes the selected Champion
6. Pressing [ Left Arrow ] undoes the last remove
7. Pressing [ Right Arrow ] exports the current list as "export.txt"
8. Pressing [ 1 ] [ 2 ] [ 3 ] or [ 4 ] will change the size of the champion icon
9. Closing the program will save the active Champions into "close.txt"

The League of Legends font can be downloaded for additional immersion.
The {title / description} is not necessary and can be omitted.

# Caution

1. An 'endline' character is required at the end of a page (essentially the last line has to be empty).
2. 'open.txt' must be 'LF' (not 'CRLF')


# TODO List

- [-] Create a minimum of one character instead of 10
- [-] Generate a list from the current use
- [-] Pressing enter to refresh the list based on open.txt
- [-] Show a 'None.png' when no '{name}.png' is found
- [-] Allow Arrow Keys for quick navigation
- [-] Crash Gracefully when 'open.txt' or 'Icons' do not exists
- [-] Pressing Backspace removes a character from the list
- [-] Pressing [ Left Arrow ] will undo the removal of a character
- [-] Resize options