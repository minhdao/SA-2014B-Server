/**
 * Created by minh on 7/22/14.
 * Only one player can win a game
 * Lose will be set for all other players
 * Continue indicates that the game can go on
 * Only one player is allowed to move at a time
 * Wait indicate that another player is playing
 */
public enum Status {
    Win,
    Valid,
    Invalid,
    Lose,
    Wait,
    Continue
}
