import keyboard
import time

def type_word(word):
    # Delay before starting to type
    time.sleep(3)

    # Type each character in the word
    while True:
        for char in word:
            keyboard.press(char)
            keyboard.release(char)
            # Delay between each character (adjust as needed)

        # Press Enter after typing the word
        keyboard.press("enter")
        keyboard.release("enter")

# Usage example
word_to_type = "Kill yourself faggot"
type_word(word_to_type)