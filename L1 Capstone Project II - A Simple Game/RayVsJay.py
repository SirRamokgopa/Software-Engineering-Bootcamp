# This is based on example.py in Task 15 of Level 1 in HyperionDev's 
# software engineering bootcamp

import pygame # Imports PyGame library.
import random # Import to generate random numbers. 


# Initialize the pygame modules to get everything started.
pygame.init() 

# The screen that will be created needs a width and a height.
screen_width = 900
screen_height = 500
# This creates the screen and gives it the width and height specified as a 2 item sequence.
screen = pygame.display.set_mode((screen_width,screen_height))

# This creates the player and gives it the image found in this folder (similarly with the enemy image). 
player = pygame.image.load("game/Zuma.jpg")
enemy1 = pygame.image.load("game/Zondo.png")
enemy2 = pygame.image.load("game/Zondo.png")
enemy3 = pygame.image.load("game/Zondo.png")
prize  = pygame.image.load("game/SA.png")

# Set the enemy speed
enemy_speed = 1.2

# Get the width and height of the images in order to do boundary detection (i.e. make sure the image stays within screen boundaries or know when the image is off the screen).

player_height = player.get_height()
player_width = player.get_width()

enemy1_height = enemy1.get_height()
enemy1_width = enemy1.get_width()

enemy2_height = enemy2.get_height()
enemy2_width = enemy2.get_width()

enemy3_height = enemy3.get_height()
enemy3_width = enemy3.get_width()

prize_height = prize.get_height()
prize_width = prize.get_width()

# Store the positions of the player and enemy as variables so that you can change them later. 
player_X_position = 100
player_Y_position = 50

# Make the enemy start off screen and at a random y position.
enemy1_X_position =  screen_width
enemy1_Y_position =  random.randint(0, screen_height - enemy1_height)
enemy2_X_position =  screen_width
enemy2_Y_position =  random.randint(0, screen_height - enemy2_height)
enemy3_X_position =  screen_width
enemy3_Y_position =  random.randint(0, screen_height - enemy3_height)

# Make prize appear in random location
prize_X_position =  random.randint(300, screen_width - prize_width)
prize_Y_position =  random.randint(0, screen_height - prize_height)

# This checks if the up or down key is pressed.
up_btn= False
down_btn = False
left_btn = False
right_btn = False

# Keeps prize hidden 
can_get_prize = False

# This is the game loop. Updates the screen window and applies changes. 

while 1: # This is the game loop. 

    screen.fill("grey")    # fills the background surface with a solid colour.
    screen.blit(player, (player_X_position, player_Y_position))    # This draws the player image to the screen at the postion (player_X_position, player_Y_position).
    screen.blit(enemy1, (enemy1_X_position, enemy1_Y_position))
    screen.blit(enemy2, (enemy2_X_position, enemy2_Y_position))
    screen.blit(enemy3, (enemy3_X_position, enemy3_Y_position))

    if can_get_prize:
        # Displays the prize
        screen.blit(prize, (prize_X_position, prize_Y_position))

    
    pygame.display.flip()# This updates the screen. 
    
    # This loops through events in the game.
    for event in pygame.event.get():
    
        # This event checks if the user quits the program, then if so it exits the program. 
        if event.type == pygame.QUIT:
            pygame.quit()
            exit(0)

        # This event checks if the user press a key down.
        if event.type == pygame.KEYDOWN:
            # Test if the key pressed is the one we want.
            if event.key == pygame.K_UP: # pygame.K_UP represents a keyboard key constant. 
                up_btn = True
            if event.key == pygame.K_DOWN:
                down_btn = True
            if event.key == pygame.K_LEFT: # pygame.K_UP represents a keyboard key constant. 
                left_btn = True
            if event.key == pygame.K_RIGHT:
                right_btn = True
        
        # This event checks if the key is up.
        if event.type == pygame.KEYUP:
            # Test if the key released is the one we want.
            if event.key == pygame.K_UP:
                up_btn = False
            if event.key == pygame.K_DOWN:
                down_btn = False
            if event.key == pygame.K_LEFT: # pygame.K_UP represents a keyboard key constant. 
                left_btn = False
            if event.key == pygame.K_RIGHT:
                right_btn = False
            
    # After events are checked for in the for loop above and values are set,
    # check key pressed values and move player accordingly.
    
    # This moves the player up and makes sure that the user does not move the player above the window.
    if up_btn == True and player_Y_position > 0 :  
        player_Y_position -= 1

    # This moves the player down and makes sure that the user does not move the player below the window.
    if down_btn == True and player_Y_position < screen_height - player_height:    
        player_Y_position += 1

    # This moves the player left and makes sure that the user does not move the player off the left the window.
    if left_btn == True and player_X_position > 0 :   
        player_X_position -= 1

    # This moves the player right and makes sure that the user does not move the player off the right of the window.
    if right_btn == True and player_X_position < screen_width - player_width:
        player_X_position += 1
    
    # Check for collision of the enemy with the player.
    # To do this we need to test if bounding boxes around the images of the player and enemy intersect. If they do then there is a collision.
    
    # Bounding box for the player:
    playerBox = pygame.Rect(player.get_rect())
    
    # Updates the playerBox position to the player's position
    playerBox.top = player_Y_position
    playerBox.left = player_X_position
    
    # Bounding box for the enemy:
    enemy1Box = pygame.Rect(enemy1.get_rect())
    enemy1Box.top = enemy1_Y_position
    enemy1Box.left = enemy1_X_position
    
    enemy2Box = pygame.Rect(enemy2.get_rect())
    enemy2Box.top = enemy2_Y_position
    enemy2Box.left = enemy2_X_position
    
    enemy3Box = pygame.Rect(enemy3.get_rect())
    enemy3Box.top = enemy3_Y_position
    enemy3Box.left = enemy3_X_position

    # Bounding box for the prize:
    prizeBox = pygame.Rect(prize.get_rect())
    prizeBox.top = prize_Y_position
    prizeBox.left = prize_X_position
    
    # Test collision of the boxes:
    if playerBox.colliderect(enemy1Box) or playerBox.colliderect(enemy2Box) or playerBox.colliderect(enemy3Box):
        # Display losing status to the user: 
        print("Hadé. You lose! Ray Zondo finally got to you.")
       
        # Quite game and exit window: 
        pygame.quit()
        exit(0)

    # Test for collusion with the prize
    if playerBox.colliderect(prizeBox) and can_get_prize:
        # Display wining status to the user: 
        print("Nice, you Captured the state!")
        
        # Quite game and exit window: 
        pygame.quit()
        exit(0)
        
    # If enemy 1 is off the screen, move enemy 2:
    if enemy1_X_position < 0 - enemy1_width:
        # If the enemy 2 is off the screen, move enemy 3:
        if enemy2_X_position < 0 - enemy2_width:
            # If the enemy 3 is off the screen, the user can get the prize
            if enemy3_X_position < 0 - enemy3_width:
                # Reveal the prize
                can_get_prize = True
            else:
                # Move enemy 3 
                enemy3_X_position -= enemy_speed
        else: 
            # Move enemy 2 
            enemy2_X_position -= enemy_speed
    else:
        # Move enemy 1.
        enemy1_X_position -= enemy_speed
    
    # ================The game loop logic ends here. =============
  
