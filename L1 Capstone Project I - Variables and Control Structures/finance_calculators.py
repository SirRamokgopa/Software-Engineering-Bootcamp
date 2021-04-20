import math

message = """
Choose either 'investment' or 'bond' to proceed:
investment  - to calculate the amount of intrest you'll earn on intrest
bond        - to calculate the amount you'll have to pay on a home loan

"""
# Ask the user to choose a calculator
method = input(message).lower()

if method == "bond":
    # Ask user for investment details
    principal = float(input("How much is your initial investment? (in R): "))
    intrest_rate = float(input("How much is your monthly intrest rate? (in %): "))/100
    period = int(input("How many months do you plan on repaing the bond over?: "))
    
    # Calculate the final amount
    installment = (intrest_rate * principal)/(1 - (1 + intrest_rate)**(-period))
    
    print(f"Monthly installments will be R{installment:.2f}")

elif method == "investment":
    # Ask user for investment details
    principal = float(input("How much is your initial investment? (in R): "))
    intrest_rate = float(input("How much is your intrest rate? (in %): "))/100
    intrest = input("Is the intrest 'simple' or 'compound'?: ")
    period = int(input("How many years do you plan on investing for?: "))
   
    # calculate the final amount
    if intrest == "simple": 
        final_amount = principal * (1 + intrest_rate * period)
    elif intrest == "compound": 
        final_amount = principal * (1 + intrest_rate)**period
   
    print(f"Final amount will be R{final_amount:.2f}")

