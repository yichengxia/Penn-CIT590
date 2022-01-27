import unittest
from pick_your_letters import *

class PickYourLetters_Test(unittest.TestCase):
    def test_filter_word_list(self):
        # test case 1:
        words = ["Given", "a", "list", "of", "words", "and", "a", "number", "returns", "a", "list",
                 "of", "words", "with", "the", "specific"]
        filtered_words = filter_word_list(words, 3)
        for word in filtered_words:
            self.assertEqual(3, len(word))

        # test case 2:
        filtered_words = filter_word_list(words, 10)
        self.assertEqual(0, len(filtered_words))

        # test case 3:
        another_words = ["sad", "safe", "sail", "salt", "same", "sand", "save", "say", "school",
                         "science", "scissors", "search", "seat", "second", "see", "seem", "sell",
                         "send", "sentence", "serve", "seven", "several", "sex", "shade", "shadow", "shake",
                         "shape", "share", "sharp", "she", "sheep", "sheet", "shelf", "shine", "ship", "shirt",
                         "shoe", "shoot", "shop", "short", "should", "shoulder", "shout", "show", "sick", "side",
                         "signal", "silence", "silly", "silver", "similar", "simple", "single", "since", "sing",
                         "sink", "sister", "sit", "six", "size", "skill", "skin", "skirt", "sky", "sleep", "slip",
                         "slow", "small", "smell", "smile", "smoke", "snow", "so", "soap", "sock", "soft", "some",
                         "someone", "something", "sometimes", "son", "soon", "sorry", "sound", "soup", "south",
                         "space", "speak", "special", "speed", "spell", "spend", "spoon", "sport", "spread",
                         "spring", "square", "stamp", "stand", "star", "start", "station", "stay", "steal",
                         "steam", "step", "still", "stomach", "stone", "stop", "store", "storm", "story",
                         "strange", "street", "strong", "structure", "student", "study", "stupid",
                         "subject", "substance", "successful", "such", "sudden", "sugar", "suitable",
                         "summer", "sun", "sunny", "support", "sure", "surprise", "sweet", "swim", "sword"]
        another_filtered_words = filter_word_list(another_words, 3)
        self.assertCountEqual(["sad", "say", "see", "sex", "she",
                               "sit", "six", "sky", "son", "sun"], another_filtered_words)

    def test_set_up(self):
        main_pile, discard_pile = set_up(7)
        self.assertEqual(7 * 26, len(main_pile))
        self.assertEqual(0, len(discard_pile))
        letters = ['a', 'b', 'c', 'd', 'e', 'd', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
                   'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z']
        for letter in letters:
            self.assertEqual(7, main_pile.count(letter))

    def test_shuffle_card(self):
        # deal with main_pile, discard_pile
        main_pile, discard_pile = set_up(7)
        # make a copy of main_pile
        copy_of_main = main_pile.copy()
        self.assertListEqual(copy_of_main, main_pile)

        # shuffle
        shuffle_cards(main_pile)

        # test shuffle
        self.assertCountEqual(copy_of_main, main_pile)
        self.assertFalse(main_pile == copy_of_main)

    def test_deal_initial_card(self):
        main_pile, discard_pile = set_up(7)
        shuffle_cards(main_pile)
        # make a copy of the shuffled main_pile
        # as the original main_pile before dealing cards to users & discard_pile
        copy_of_shuffled_main = main_pile.copy()

        # dealing cards
        human_hand, computer_hand = deal_initial_cards(main_pile, discard_pile, 7)
        # check the amount of main_pile after dealing initial cards
        self.assertEqual(7 * 26 - 7 * 2 - 1, len(main_pile))

        # note that computer is always the first person that gets dealt
        # get slice of copy_of_shuffled_main by using start index:end index:index jump
        self.assertEqual(copy_of_shuffled_main[1:15:2], human_hand)
        self.assertEqual(copy_of_shuffled_main[0:14:2], computer_hand)
        self.assertEqual([copy_of_shuffled_main[2 * 7]], discard_pile)

    def test_move_to_discard(self):
        main_pile, discard_pile = set_up(7)
        move_to_discard_pile(discard_pile, "h")
        move_to_discard_pile(discard_pile, "e")
        move_to_discard_pile(discard_pile, "l")
        move_to_discard_pile(discard_pile, "l")
        move_to_discard_pile(discard_pile, "o")

        # note: the top item of one pile is the item at index 0
        self.assertEqual(["o", "l", "l", "e", "h"], discard_pile)

    def test_get_first_from_pile_and_remove(self):
        main_pile, discard_pile = set_up(7)
        copy_of_main = main_pile.copy()
        self.assertEqual(copy_of_main[0], get_first_from_pile_and_remove(main_pile))
        # the first item get removed
        self.assertEqual(copy_of_main[1:], main_pile)

    def test_check_bricks(self):
        # here, we use the set_up(length) function as a helper function
        # to get an empty list and one non-empty list
        discard_pile, main_pile = set_up(7)
        copy_of_shuffled_discard = discard_pile.copy()
        check_bricks(main_pile, discard_pile)
        self.assertEqual(7 * 26 - 1, len(main_pile))
        self.assertEqual(1, len(discard_pile))
        self.assertCountEqual(copy_of_shuffled_discard, main_pile + discard_pile)

    def test_check_game_over(self):
        words = ["sad", "safe", "sail", "salt", "same", "sand", "save", "say", "school",
                 "science", "scissors", "search", "seat", "second", "see", "seem", "sell",
                 "send", "sentence", "serve", "seven", "several", "sex", "shade", "shadow", "shake",
                 "shape", "share", "sharp", "she", "sheep", "sheet", "shelf", "shine", "ship", "shirt",
                 "shoe", "shoot", "shop", "short", "should", "shoulder", "shout", "show", "sick", "side",
                 "signal", "silence", "silly", "silver", "similar", "simple", "single", "since", "sing",
                 "sink", "sister", "sit", "six", "size", "skill", "skin", "skirt", "sky", "sleep", "slip",
                 "slow", "small", "smell", "smile", "smoke", "snow", "so", "soap", "sock", "soft", "some",
                 "someone", "something", "sometimes", "son", "soon", "sorry", "sound", "soup", "south",
                 "space", "speak", "special", "speed", "spell", "spend", "spoon", "sport", "spread",
                 "spring", "square", "stamp", "stand", "star", "start", "station", "stay", "steal",
                 "steam", "step", "still", "stomach", "stone", "stop", "store", "storm", "story",
                 "strange", "street", "strong", "structure", "student", "study", "stupid",
                 "subject", "substance", "successful", "such", "sudden", "sugar", "suitable",
                 "summer", "sun", "sunny", "support", "sure", "surprise", "sweet", "swim", "sword"]
        words_with_length_three = filter_word_list(words, 3)

        human_hand = ["s", "o", "s"]
        computer_hand = ['l', 'o', 'l']
        # game doesn't end
        self.assertFalse(check_game_over(human_hand, computer_hand, words_with_length_three))

        human_hand = ["s", "o", "n"]
        # human wins, because human_hand word is "son", however computer's is "lol"
        self.assertTrue(check_game_over(human_hand, computer_hand, words_with_length_three))

        computer_hand = ["s", 'a', 'y']
        # tie, because human_hand word is still "son", and computer_hand's is "say".
        # return True as well
        self.assertTrue(check_game_over(human_hand, computer_hand, words_with_length_three))


if __name__ == '__main__':
    unittest.main()
