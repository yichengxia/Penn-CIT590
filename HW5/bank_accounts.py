# Name: Yicheng Xia
# Penn ID: 65349745
# Statement of work: I worked alone without help.


def init_bank_accounts(accounts, deposits, withdrawals):
    """
    Loads the given 3 files
    Stores the information for individual bank accounts in a dictionary
    Calculates the account balance
    Returns the 'bank_accounts' dictionary
    """
    # initialize empty bank_accounts dict
    bank_accounts = {}

    # read accounts_data and save it to bank_accounts
    with open(accounts) as accounts_data:
        for line in accounts_data:
            # split by '|' and strip whitespaces
            lst = line.strip().split('|')
            lst = [i.strip() for i in lst]
            # record lst values to sub_dict
            sub_dict = {'first_name': lst[1], 'last_name': lst[2], 'balance': 0.0}
            # record bank_accounts values
            bank_accounts[lst[0]] = sub_dict
    
    # read deposits_data and save it to bank_accounts
    with open(deposits) as deposits_data:
        for line in deposits_data:
            # split by ',' and strip whitespaces
            lst = line.strip().split(',')
            # cast list of deposits strings to values
            lst[1:] = [float(lst[i]) for i in range(len(lst)) if i > 0]
            # call deposit() to record on bank_accounts
            deposit(bank_accounts, lst[0], float(str(sum(lst[1:]))))
    
    # read withdrawals_data and update it to bank_accounts
    with open(withdrawals) as withdrawals_data:
        for line in withdrawals_data:
            # split by ',' and strip whitespaces
            lst = line.strip().split(',')
            # cast list of deposits strings to values
            lst[1:] = [float(lst[i]) for i in range(len(lst)) if i > 0]
            # call withdraw() to calculate the total balance for each account by taking the total deposit
            withdraw(bank_accounts, lst[0], float(str(sum(lst[1:]))))
    
    return bank_accounts


def get_account_info(bank_accounts, account_number):
    """
    Returns the account information for the given account_number as a dictionary
    If the account doesn't exist, returns None
    """
    return bank_accounts.get(account_number)


def withdraw(bank_accounts, account_number, amount):
    """
    Withdraws the given amount from the account with the given account_number
    Rounds the new balance to 2 decimal places (Uses round_balance() function)
    """
    # if the account does not exist, print a friendly message and return
    if get_account_info(bank_accounts, account_number) == None:
        print("Sorry! This is an invalid account! :(")
        return

    # if the given amount is greater than the available balance, raise a Runtime Error
    if bank_accounts[account_number]['balance'] < amount:
        raise RuntimeError("Sorry! Amount greater than available balance! :(")
    
    # update, round and print the new balance
    bank_accounts[account_number]['balance'] -= amount
    round_balance(bank_accounts, account_number)
    print("New balance: {}".format(bank_accounts[account_number]['balance']))


def deposit(bank_accounts, account_number, amount):
    """
    Deposits the given amount into the account with the given account_number
    Rounds the new balance to 2 decimal places (Uses round_balance() function)
    """
    # if the account does not exist, print a friendly message
    if get_account_info(bank_accounts, account_number) == None:
        print("Sorry! This is an invalid account! :(")
        return
    
    # update, round and print the new balance
    bank_accounts[account_number]['balance'] += amount
    round_balance(bank_accounts, account_number)
    print("New balance: {}".format(bank_accounts[account_number]['balance']))


def purchase(bank_accounts, account_number, amounts):
    """
    Makes a purchase with the total of the given amounts from the account with the given account_number
    amounts is a list of floats
    """
    # if the account does not exist, print a friendly message
    if get_account_info(bank_accounts, account_number) == None:
        print("Sorry! This is an invalid account! :(")
        return

    # calculate the total purchase amount, plus 6% sales tax
    total_amount = sum(amounts) + calculate_sales_tax(sum(amounts))

    # if the given amount is greater than the available balance, raise a Runtime Error
    if bank_accounts[account_number]['balance'] < total_amount:
        raise RuntimeError("Sorry! Amount greater than available balance! :(")

    # update, round and print the new balance
    bank_accounts[account_number]['balance'] -= total_amount
    round_balance(bank_accounts, account_number)
    print("New balance: {}".format(bank_accounts[account_number]['balance']))


def sort_accounts(bank_accounts, sort_type, sort_direction):
    """
    Converts the key: value pairs in the given bank_accounts dictionary to a list of tuples
    and sorts based on the given sort_type and sort_direction
    Returns the sorted list of tuples
    """
    # create tmp_set to check if sort_type will be accepted
    # if not, return
    tmp_set = {'account_number', 'first_name', 'last_name', 'balance'}
    if sort_type not in tmp_set:
        print("Sorry! This is an invalid sort type! Use 'account_number', 'first_name', 'last_name', or 'balance'! :(")
        return

    # covert bank_accounts dict to list of tuples
    bank_accounts = [(k, v) for k, v in bank_accounts.items()]
    # if sort_type == 'account_number', sort by bank_accounts keys
    if sort_type == 'account_number':
        bank_accounts = sorted(bank_accounts, key = lambda x: int(x[0]), reverse = is_reverse(sort_direction))
    # else sort by sub dict keys
    else:
        bank_accounts = sorted(bank_accounts, key = lambda x: x[1][sort_type], reverse = is_reverse(sort_direction))

    return bank_accounts


def is_reverse(sort_direction):
    """
    Returns reverse label
    If sort_direction == 'asc', return False
    If sort_direction == 'desc', return True (default)
    """
    return not sort_direction == 'asc'


def export_statement(bank_accounts, account_number, output_file):
    """
    Exports the given account information to the given output file in the following format:
        First Name: Huize
        Last Name: Huang
        Balance: 34.57
    """
    file = open(output_file, 'w')
    # initialize empty tmp_lst
    tmp_lst = []
    # append each item to tmp_lst
    tmp_lst.append("First Name: {}\n".format(bank_accounts[account_number]['first_name']))
    tmp_lst.append("Last Name: {}\n".format(bank_accounts[account_number]['last_name']))
    tmp_lst.append("Balance: {}\n".format(bank_accounts[account_number]['balance']))

    # write all lines in tmp_lst and close
    file.writelines(tmp_lst)
    file.close()


def round_balance(bank_accounts, account_number):
    """
    Rounds the account balance of the given account_number to two decimal places
    """
    bank_accounts[account_number]['balance'] = round(bank_accounts[account_number]['balance'], 2)


def calculate_sales_tax(amount):
    """
    Calculates and returns a 6% sales tax for the given amount
    """
    return 0.06 * amount


def main():
    # load and get all account info
    bank_accounts = init_bank_accounts('accounts.txt', 'deposits.csv', 'withdrawals.csv')

    # banking system loop starts
    while True:
        print("\nWelcome to Penn Bank! What would you like to do?")
        print("1: Get account info")
        print("2: Make a deposit")
        print("3: Make a withdrawal")
        print("4: Make a purchase")
        print("5: Sort accounts")
        print("6: Export a statement")
        print("0: Leave the bank")

        # take input choice
        request = input()

        # check if request is valid
        # try to cast request to an int
        try:
            request = int(request)
        # throw an exception if casting fails
        except ValueError:
            print("Sorry! This is an invalid request! :(")
            continue
        # check if request is 0 to 6
        if request < 0 or request > 6:
            print("Sorry! This is an invalid request! :(")
            continue
        
        # 1: Get account info
        if request == 1:
            account_number = input("Account number: ")
            # get_account_info and print its return value
            print(get_account_info(bank_accounts, account_number))

        # 2: Make a deposit
        elif request == 2:
            account_number = input("Account number: ")
            amount = input("Deposit Amount: ")
            # try to cast amount to a float
            try:
                amount = float(amount)
            # throw an exception if casting fails
            except ValueError:
                print("Sorry! This is an invalid amount! :(")
                continue
            # if the amount is negative, prints a friendly message
            if amount < 0.0:
                print("Sorry! This is an invalid amount! :(")
                continue
            # call deposit() to make a deposit
            deposit(bank_accounts, account_number, amount)

        # 3: Make a withdrawal
        elif request == 3:
            account_number = input("Account number: ")
            amount = input("Withdrawl Amount: ")
            # try to cast amount to a float
            try:
                amount = float(amount)
            # throw an exception if casting fails
            except ValueError:
                print("Sorry! This is an invalid amount! :(")
                continue
            # call withdraw() to make a withdrawal
            withdraw(bank_accounts, account_number, amount)

        # 4: Make a purchase
        elif request == 4:
            account_number = input("Account number: ")
            amounts = input("Amounts (as comma separated list): ")
            # split by ',' and strip whitespaces
            amounts = amounts.strip().split(',')
            amounts = [amount.strip() for amount in amounts]
            # try to cast amounts to a list of floats
            try:
                amounts = [float(amount) for amount in amounts]
            # throw an exception if casting fails
            except ValueError:
                print("Sorry! This contains an invalid amount! :(")
                continue
            # call purchase() to make the purchase
            purchase(bank_accounts, account_number, amounts)

        # 5: Sort accounts
        elif request == 5:
            sort_type = input("Sort type ('account_number', 'first_name', 'last_name', or 'balance'): ")
            sort_direction = input("Sort type ('asc' or 'desc'): ")
            # call sort_accounts() and print its return value
            print(sort_accounts(bank_accounts, sort_type, sort_direction))

        # 6: Export a statement
        elif request == 6:
            account_number = input("Account number: ")
            # if the account does not exist, print a friendly message
            if get_account_info(bank_accounts, account_number) == None:
                print("Sorry! This is an invalid account! :(")
                continue
            # set output_file name with account_number
            output_file = account_number + ".txt"
            # export_statement to create and write output_file
            export_statement(bank_accounts, account_number, output_file)

        # 0: Leave the bank
        elif request == 0:
            break

    print("\nGoodbye! Have a nice day! - Penn Bank :)")


if __name__ == "__main__":
    main()
