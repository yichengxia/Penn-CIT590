# Name: Yicheng Xia
# Penn ID: 65349745
# Statement of work: I worked alone without help.

import random
import string

def read_from_file(file_name):
    """
    Reads all words from file
    Parameter file_name is the name of the file
    This function returns a list containing all the words
    """
    f = open(file_name, "r")
    all_words = f.read().splitlines()
    f.close()
    return all_words


def ask_for_length():
    """
    Ask the user for the number of hand cards
    Prompt again if the user input is not a valid integer or not between 3 to 10 (inclusive)
    Return the number of hand cards L
    """
    # take the length of words from input message
    length = input("Enter a number between 3 - 10 to be length of the word you are going to guess: ")
    try:
        # try to cast length to an integer
        length = int(length)
    except ValueError:
        # throw an exception if casting fails and return itself
        print("Sorry! Only integers between 3 - 10 are accepted! :(")
        return ask_for_length()
    if length < 3 or length > 10:
        # if the length is outside the range, return itself
        print("Sorry! Only integers between 3 - 10 are accepted! :(")
        return ask_for_length()
    return length


def filter_word_list(all_words, length):
    """
    Given a list of words, and a number, returns a list of words with the specific length
    Parameter all_words is the list of all words
    Parameter length is the given length
    """
    filtered_words = []
    for word in all_words:
        # if any word in all_words has the required length, append it to fitered_words
        if len(word) == length:
            filtered_words.append(word)
    return filtered_words


def set_up(length):
    """
    Run this function once after the length of words is determined and the list of words is filtered
    Create a main pile of 26 * length cards, represented as a list of lowercase letters, with length of each letter
    Create a discard pile of 0 cards, represented as an empty list
    Return both lists as a tuple, with the main pile as the first item and the discard pile as the second item
    Parameter length is the given length
    """
    # initiate main_pile with a list of lowercase letters of length times
    main_pile = list(string.ascii_lowercase) * length
    # initiate empty discard_pile list
    discard_pile = []
    return (main_pile, discard_pile)


def shuffle_cards(pile):
    """
    Parameter pile is the given list of words
    Shuffle the given pile and doesn’t return anything
    """
    random.shuffle(pile)


def move_to_discard_pile(discard_pile, card):
    """
    Move the given card to the top of the discard pile
    Parameter discard_pile is the discard pile
    Parameter card is the given letter to be discarded
    Return nothing
    """
    discard_pile.insert(0, card)


def deal_initial_cards(main_pile, discard_pile, length):
    """
    Start the game by dealing two sets of length cards each, from the given main_pile
    The computer is always the first person that gets dealt to and always plays first
    Remove the card on top of the main pile and put it on the discard pile
    Returns a tuple containing two lists
    The first one representing the human’s hand and the second one representing the computer’s hand
    """
    #initiate empty human_hand_cards and computer_hand_cards
    human_hand_cards = []
    computer_hand_cards = []
    
    # deal first cards respectively
    # pop and append them to users' hands
    for i in range(length):
        computer_hand_cards.append(main_pile.pop(0))
        human_hand_cards.append(main_pile.pop(0))

    # pop and append another card to discard_pile
    discard_pile.append(main_pile.pop(0))
    return (human_hand_cards, computer_hand_cards)


def get_first_from_pile_and_remove(pile):
    """
    Return and remove the first item of the given list
    Parameter pile is the list from which to remove the first element
    """
    return pile.pop(0)


def check_bricks(main_pile, discard_pile):
    """
    Check whether the main_pile is empty.
    If so, shuffles the discard_pile and moves all the cards to the main_pile.
    Then turn over the top card of the main_pile to be the start of the new discard_pile.
    Otherwise, do nothing
    """
    if len(main_pile) == 0:
        shuffle_cards(discard_pile)
        # use main_pile[:] and copy() to modify original main_pile list
        main_pile[:] = discard_pile.copy()
        # use discard_pile[:], list() and pop(0) to modify original discard_pile list
        discard_pile[:] = list(main_pile.pop(0))


def computer_play(computer_hand_cards, computer_target_list, main_pile, discard_pile):
    """
    For a given letter, the computer decides whether to take it or not
    Parameter computer_hand_cards is the computer’s hand cards
    Parameter main_pile is the main pile
    Parameter discard_pile is the discard pile
    Parameter computer_target_list is a list of words
    Return nothing
    """
    # concatenate computer_hand_word as a string for comparisons
    computer_hand_word = ''.join(computer_hand_cards)

    # set word length and number with computer_target_list
    length = len(computer_target_list[0])
    number = len(computer_target_list)

    # set the top card from DISCARD PILE as temp_card
    temp_card = discard_pile[0]

    # initialize compare_index for documenting different letter positions
    compare_index = [''] * number
    # initialize compare_string for documenting different letters
    compare_string = [''] * number

    # count starts from 1 for documenting the number of different letters
    count = 1

    for i in range(number):
        # check if already have a word on list
        # if so, reveal and discard the top card from MAIN PILE, and return
        if computer_hand_word == computer_target_list[i]:
            temp_card = get_first_from_pile_and_remove(main_pile)
            print("Computer revealed '{}' from MAIN PILE.".format(temp_card))
            print("Computer discarded '{}' to DISCARD PILE.".format(temp_card))
            move_to_discard_pile(discard_pile, temp_card)
            # print computer hand cards again
            print("Computer's current hand cards are: {}.".format(computer_hand_cards))
            return
        # compare every letter of computer_hand_word with that on computer_target_list
        for j in range(length):
            # if they differ, update compare_index and compare_string
            if computer_hand_word[j] != computer_target_list[i][j]:
                compare_index[i] += str(j)
                compare_string[i] += computer_target_list[i][j]

    # first, consider DISCARD PILE for it already has an open card
    for i in range(number):
        # if only one letter is different, exchange them, and return
        # this will immediately lead to computer's win
        if len(compare_index[i]) == 1 and compare_string[i] == temp_card:
            print("Computer picked '{}' from DISCARD PILE.".format(temp_card))
            print("Computer replaced '{}' with '{}'.".format(computer_hand_cards[int(compare_index[i])], temp_card))
            computer_hand_cards[int(compare_index[i])], discard_pile[0] = discard_pile[0], computer_hand_cards[int(compare_index[i])]
            # print updated computer hand cards
            print("Computer's current hand cards are: {}.".format(computer_hand_cards))
            return

    # second, consider the letter from MAIN PILE and reveal it
    temp_card = get_first_from_pile_and_remove(main_pile)
    print("Computer revealed the '{}' from MAIN PILE.".format(temp_card))

    # while loop starts from count == 1
    while count <= length:
        for i in range(number):
            # if the number of different letters of the indexed word is count
            if len(compare_index[i]) == count:
                for j in range(count):
                    # with the minimum count
                    # if temp_card has same letter to reduce the different letters for some word, exchange them and return
                    if compare_string[i][j] == temp_card:
                        print("Computer replaced '{}' with '{}'.".format(computer_hand_cards[int(compare_index[i][j])], temp_card))
                        discard_pile.insert(0, computer_hand_cards.pop(int(compare_index[i][j])))
                        computer_hand_cards.insert(int(compare_index[i][j]), temp_card)
                        # print updated computer hand cards
                        print("Computer's current hand cards are: {}.".format(computer_hand_cards))
                        return
        # if still no return, consider more different letters by + 1
        count += 1


def ask_for_the_letter_to_be_replaced(length):
    """
    Ask for the index of the letter that the user wants to replace
    Prompt again if the input index is out of range or invalid
    Parameter length is the number of cards in the human’s hand
    Return the index of the letter to be replaced
    """
    # take the index of the letter to change from input message
    index = input("Please select the index of the letter to be replaced, e.g. '1': ")
    try:
        # try to cast index to an integer
        index = int(index)
    except ValueError:
        # throw an exception if casting fails and return itself
        print("Sorry! Only valid integers are accepted! :(")
        return ask_for_the_letter_to_be_replaced(length)
    if index < 0 or index >= length:
        # if the index is outside the range, return itself
        print("Sorry! Only valid integers are accepted! :(")
        return ask_for_the_letter_to_be_replaced(length)
    return index


def ask_yes_or_no(msg):
    """
    Display msg and get user's response
    Prompt again if the input is invalid
    Parameter msg is the message to display
    Return True if the user answers 'y' or 'yes'
    Return False if the user answers 'n' or 'no'
    """
    # take ans from input message
    ans = input(msg)
    if ans == 'y' or ans == 'yes':
        return True
    elif ans == 'n' or ans == 'no':
        return False
    else:
        # else means ans is invalid, so return itself instead
        print("Sorry! Your input is invalid! :(")
        return ask_yes_or_no(msg)


def ask_d_or_m(msg):
    """
    Display msg and get user's response
    Prompt again if the input is invalid
    Parameter msg is the message to display
    Return True if the user answers 'D' or 'd'
    Return False if the user answers 'M' or 'm'
    """
    # take ans from input message
    ans = input(msg)
    if ans == 'D' or ans == 'd':
        return True
    elif ans == 'M' or ans == 'm':
        return False
    else:
        # else means ans is invalid, so return itself instead
        print("Sorry! Your input is invalid! :(")
        return ask_d_or_m(msg)


def check_game_over(human_hand_cards, computer_hand_cards, words_with_specific_length):
    """
    Check if the game ends
    If there is a tie, the game ends as well
    Parameter human_hand_cards is the human’s current hand (list)
    Parameter computer_hand_cards is the computer’s current hand (list)
    Parameter words_with_specific_length is a list containing all the words with the specific length
    Return True if the human or the computer wins the game, otherwise False
    """
    # concatenate human_hand_word and computer_hand_word as strings for comparisons
    human_hand_word = ''.join(human_hand_cards)
    computer_hand_word = ''.join(computer_hand_cards)

    # set human_win and computer_win to mark each win status
    human_win = False
    computer_win = False

    # compare words from each sides with words on filtered list
    # if they match, set the status of that side as True
    for word in words_with_specific_length:
        # loop breaks when both win
        # otherwise it continues searching
        if human_win and computer_win:
            break
        if not human_win:
            if human_hand_word == word:
                human_win = True
        if not computer_win:
            if computer_hand_word == word:
                computer_win = True
    
    # if both win (a tie)
    if human_win and computer_win:
        print("--------------------------------------------------")
        # print human's word
        print("Your word is {}.".format(human_hand_word))
        # print computer's word 
        print("Computer's word is {}.".format(computer_hand_word))
        print("Neither side wins! It is a tie! :(")
        return True
    # elif only human wins
    elif human_win:
        print("--------------------------------------------------")
        # print human's word
        print("Your word is {}.".format(human_hand_word))
        print("Congratulations! You win! :)")
        return True
    # elif only computer wins
    elif computer_win:
        print("--------------------------------------------------")
        # print computer's word 
        print("Computer's word is {}.".format(computer_hand_word))
        print("Wow! Computer wins! :)")
        return True
    # else the game is not over, so return False
    else:
        return False


def main():
    # reads all words from file
    all_words = read_from_file("words.txt")

    print("Welcome to the game!")

    # ask for a number as the length of the word
    length = ask_for_length()

    # filter all_words with a length equal to the given length
    filtered_words = filter_word_list(all_words, length)

    # set up main_pile and discard_pile
    main_pile, discard_pile = set_up(length)

    # shuffle main pile
    shuffle_cards(main_pile)

    # deal cards to players, creating human_hand_cards and computer_hand_cards
    # and initialize discard pile
    human_hand_cards, computer_hand_cards = deal_initial_cards(main_pile, discard_pile, length)

    # start the game
    while True:
        # check if main_pile is empty by calling check_bricks(main_pile, discard_pile)
        check_bricks(main_pile, discard_pile)

        # computer play goes here
        print("--------------------------------------------------")
        print("Computer's turn:")
        # print computer hand cards
        print("Computer's hand cards are {}.".format(computer_hand_cards))
        computer_play(computer_hand_cards, filtered_words, main_pile, discard_pile)

        # check if main_pile is empty again since computer may have changed MAIN PILE
        check_bricks(main_pile, discard_pile)

        # human play goes here
        print("--------------------------------------------------")
        print("Your turn:")
        # print human hand cards
        print("Your hand cards are {}.".format(human_hand_cards))
        # ask human to choose from DISCARD PILE or MAIN PILE
        print("Would you like to pick '{}' from DISCARD PILE or reveal the letter from MAIN PILE?".format(discard_pile[0]))
        if ask_d_or_m("Type 'D' or 'M' to respond: "):
            # if human chooses DISCARD PILE, ask for the card index to change
            index = ask_for_the_letter_to_be_replaced(length)
            print("You replaced '{}' with '{}'.".format(human_hand_cards[index], discard_pile[0]))
            human_hand_cards[index], discard_pile[0] = discard_pile[0], human_hand_cards[index]
        else:
            # else human chooses MAIN PILE
            new_card = get_first_from_pile_and_remove(main_pile)
            print("You got '{}' from MAIN PILE!".format(new_card))
            print("Would you like to accepted this letter?")
            if ask_yes_or_no("Type 'y/yes' or 'n/no' to respond: "):
                # if human accepts the letter
                index = ask_for_the_letter_to_be_replaced(length)
                # print the exchange message and execute its corresponding operation
                print("You replaced '{}' with '{}'.".format(human_hand_cards[index], new_card))
                discard_pile.insert(0, human_hand_cards.pop(index))
                human_hand_cards.insert(index, new_card)
            else:
                # else human discards the letter
                # print the discard message and execute its corresponding operation
                print("You discarded '{}' to DISCARD PILE.".format(new_card))
                move_to_discard_pile(discard_pile, new_card)
        # print updated human hand cards
        print("Your current hand cards are: {}.".format(human_hand_cards))

        # check if game is over and print out results
        if check_game_over(human_hand_cards, computer_hand_cards, filtered_words):
            break

if __name__ == "__main__":
    main()
