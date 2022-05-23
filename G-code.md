# Understanding G-code

## General syntax

- Everything after `;` in a line of code is a comment
- Variables start with `#`

## O-codes

[source](https://www.linuxcnc.org/docs/html/gcode/o-code.html)

TLDR; O-codes are scopes

### Syntax:

1. O*numbers*
    - Ex:
        - O100
        - O1
        - O312
2. O*numbers* *control-flow* [_control-flow options_]
    - Ex:
        - O100 if [#1 GT 3]
        - O122 call
        - O123 call [22] [1] [4]
        - O80 endif
3. O\<_filename_\> call _[call-args]_
4. O[_expression_]
    - Ex:
        - O[#101+2]
        - O[2*4]
        - O[88] 
