expr -> term { ("+" | "-" | "⊗") term } .
term -> factor { ("*" | "/" ) factor } .
factor -> power [ "^" factor ] .
power -> primary | "-" primary | "(" expr ")" | integer .
primary -> integer | "(" expr ")" .
integer -> digit { digit } .
digit -> "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9" .
