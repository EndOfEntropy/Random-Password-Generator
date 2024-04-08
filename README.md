# Random-Password-Generator
This project is a Graphical User Interface that generates random passwords and perform password strength checks.

# Functionalities

## Generating a Password:
   - The user can select the character types: uppercase letters, lowercase letters, numbers, or symbols.
   - The user can select the desired length of the password.
   - A password character list is generated based on the user's answers, which is a string containing the chosen characters.
   - Random characters from the password character list are selected and combined to form a completely random string according to the user's preferences.
   - The randomly generated password can be masked or displayed on the GUI

## Checking the Password's Score and Strength:
   
The score check is based on the following criteria:
   - The total number of characters
   - The total number of lower case letters
   - The total number of upper case letters
   - The total number of digits
   - The total number of special characters
   - The total number of middle digits or special characters
   - The total number of validated requirements

Penalties will be incurred when:
   - The password contains only letters
   - The password contains only digits
   - The password contains consecutive lower case letters
   - The password contains consecutive upper case letters
   - The password contains consecutive digits

These criteria are used to calculate a score for the password, which determines the message displayed to the user indicating the strength of the password (very weak/weak/good/strong/very strong).


