F -> E [ "^" F ]
E -> T { ("+" | "-" | "⊗") T } .
T -> P { ("*" | "/" ) P } .
power -> primary | "-" primary | "(" expr ")" | integer .
primary -> integer | "(" expr ")" .
integer -> digit { digit } .
digit -> "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9" .
