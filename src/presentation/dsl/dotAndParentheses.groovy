package presentation.dsl

//https://en.wikipedia.org/wiki/Apache_Groovy







/**
 *   Groovy's syntax permits omitting
 *   parentheses and dots in some situations.
 *   The following groovy code
 */
take(coffee).with(sugar, milk).and(liquor)
/**
 *   can be written as
 */
take coffee with sugar, milk and liquor
/**
 *   enabling the development of
 *   domain-specific languages (DSLs)
 *   that look like plain English.
 */







