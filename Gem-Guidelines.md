# The Gem programming Language: G-code made easy

## Objectives:

- [ ] The output of any Gem program is valid G-code
- [ ] Any valid G-code program is a valid Gem program
- [ ] Programs written in Gem are easy to read and understand

## Syntax:

- Program may start with `%`
- Whitespaces are ignored

### Comments

- Single or multiline: in parentheses `(...)`
- Single line: after `;`

### Variables

#### Names:

- Local variables:
    - `$NAME` or `#<NAME>`
- Global variables:
    - `$_NAME` or `#<_NAME>`

#### Assignments:

- `$variable = value`
- `$variable [op]= value`
    - Ex: `$variable += value`
    - Ex: `$variable -= value`
    - Ex: `$variable *= value`
    - etc.

### Replacing O-codes

To replace O-codes in the program's control flow department, Gem introduces `@words`.

General syntax for each `@word`:

- `@word` + `<O-code>` (optional) + _complement_ (if needed)
    - Ex: `@if 3 > $var` OR `@if<100> 3 > $var`
    - Ex: `@else` OR `@else<100>`

> Because it is tedious to write everything twice, the following explanations won't contain the manual `<O-code>`'s
> syntax. However, remember that every time you see `@word`, you could change it to `@word<O-code>`

- ### List of all `@words`
    - Conditionals
        - `@if` _condition_
        - `@elif` _condition_
        - `@else`
    - Loops
        - `@while` _condition_
        - `@do`
        - `@for` _$variable_ = _value_ : _condition_ : _expression_
        - `@repeat` _expression_
        - `@loop`
        - `@foreach` _$variable_ `in` _range_
    - Subroutines
        - `@sub`
        - `@return`
        - `@call`

- ### Conditionals (@if, @elif, and @else)
  #### Definitions:
    1. Solo if:
        ```
        @if condition {
            ; if's body
        }
        ```
    2. If-else:
        ```
        @if condition {
            ; if's body
        } @else {
            ; else's body
        }
        ```
    3. If-elif:
        ```
        @if condition1 {
            ; if's body
        } @elif condition2 {
            ; first elif's body
        } @elif condition3 {
            ; second elif's body
        } ...
        ```
    3. If-elif-else:
        ```
        @if condition1 {
            ; if's body
        } @elif condition2 {
            ; first elif's body
        } @elif condition3 {
            ; second elif's body
        } @else {
            ; else's body
        }
        ```
  #### Examples:
    - TODO: write examples


- ### Loops (@do, @while, @for, @foreach, @repeat, and @loop)

- ### Subroutines (@sub, @return, and @call)

  #### Definitions:

    1. Automatic O-code

    ```
      @sub name[$param1, $param2, ...] {
          ; Subroutine's body
      }
    ```

    2. Manual O-code

    ```
      @sub<code> name[$param1, $param2, ...] {
          ; Subroutine's body
      }
    ```

  #### Returning:
    - `@return value` OR `@return`

  #### Calling:
    - No parameters: `@call name`
    - With parameters: `@call name[arg1, arg2, ...]`
        - `@call name[0, 2, 81]`
        - `@call name[#val1, 2, #val4]`
        - `@call name[#val1, 2, -#val4]`
        - `@call name[#val1 * 3 - #val2, 2, -3]`

  > **/!\ IMPORTANT /!\\**:  
  > Calling a function can be done in an assigments (`#abc = @call f[1, 2, 3]`) and the value the function
  > returns will be the one assigned to the variable.
  > However, a line of code can only contain **one function call**.  
  > Ex: `#abc = @call foo[1, 2] - @call bar[5, -2, 3]` is **not allowed**. Instead, you should do
  > ```
  > #abc = @call foo[1, 2]
  > #abc -= @call bar[5, -2, 3]
  > ```

### Replacing G-code's codes

#### Modal codes:

#### Non-modal codes:

## Example G-code vs Gem:

### G-code:

```
%
O2468
N0010 (ICI JE METS DES COMMENTAIRES COMME LA DATE)
N0020 (ICI JE METS DES COMMENTAIRES COMME LA LE NOM D EQUIPE)
N0030 (ICI JE METS DES COMMENTAIRES COMME LE NOM DE MONTAGE)
N0040 G00 G17 G20 G40 G80 G90 G94
N0050 G91 G30  X0. Y0. Z0. T01 M06
N0060 S1400 M03
N0070 G90 G54 X1.5 Y.5 (POINT A)
N0080 G43 Z4. H01
N0090 M08
N0100 G81 X1.5 Y.5 Z-0.4253 R.1 F10. (G98 OU G99 SELON PIECE)
N0110 X2. Y-0.5
N0120 G80 M09
N0130 G00 Z4. M05
N0140 G91 G30 Z0.
N0150 G91 G30 X0. Y0. T02 M06
N0160 S1400 M03
N0170 G90 G54 X1.5 Y.5 (POINT A)
N0180 G43 Z4. H01
N0190 M08
```

### Gem:

```
%
O2468

G00 G17 G20 G40 G80 G90 G94
!motion:rapid/feed
!plane:XY
!unit:inch      
!cutter-dcomp:none  ; Cutter Diameter Compensation
!motion:no-cc       ; cancel canned cycles
!feed-mode:minute


G91 G30  X0. Y0. Z0. T01 M06
S1400 M03
G90 G54 X1.5 Y.5 (POINT A)
G43 Z4. H01
M08
G81 X1.5 Y.5 Z-0.4253 R.1 F10. (G98 OU G99 SELON PIECE)
X2. Y-0.5
G80 M09
G00 Z4. M05
G91 G30 Z0.
G91 G30 X0. Y0. T02 M06
S1400 M03
G90 G54 X1.5 Y.5 (POINT A)
G43 Z4. H01
M08
```

### Gem:

```
@sub sum_or_sub[$decider, $x, $y] {
    @if $decider == $_True {     ; sum
        @return $x + $y
    } @else {                    ; sub
        @return $x - $y
    }
}

$salut = @call sum_or_sub[1, 2, 3]
```

### G-code:

```
N010  #<_True> = 1
N020  #<_False> = 0
N030  
N040  O100 sub     ; sum_or_sub
N050      O110 if [#1 EQ #<_True>]
N060          O100 return [#2 + #3]
N070      O110 else
N080          O100 return [#2 - #3]
N090      O110 endif
N100  O100 endsub  ; end sum_or_sub
N110  
N120  O100 call [1] [2] [3]
N130  #<salut> = #<_value>
```



































