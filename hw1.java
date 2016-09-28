import java.util.Arrays;
/**
 * System to track customer service representatives' scores.  Created with help from teacher and 
 * stackexchange for some array/loop info.
 * @author David Mandelbaum 
 * @version 9/19
 */
public class CustomerServiceRepScores
{ 
    private int repQuantity;
    private int numberOfPossibleScores;
    private int[][] scores;
    private int[][] lastTwentyScores;
    private double runningAverage;
    private boolean containsSomething;
    private boolean checkRep;

    public CustomerServiceRepScores(int repQuantity, int scoreQuantity)
    {
        this.repQuantity = repQuantity;
        this.numberOfPossibleScores = scoreQuantity;
        this.scores = new int[this.repQuantity][this.numberOfPossibleScores];
        //initialize all score counts to zero
        for(int i= 0; i< this.scores.length; i++)
        {
            Arrays.fill(this.scores[i],0);
        }
        this.lastTwentyScores = new int[this.repQuantity][20];
        for(int i= 0; i< this.lastTwentyScores.length; i++)
        {
            Arrays.fill(this.lastTwentyScores[i],0);
        }
        runningAverage = 0;
        containsSomething = false;
        checkRep = false;
    }

    /**
     * Add a new score for rep
     * @param repID the representative who received this score.
     * @param score the score received
     */
    public void addNewScore(int repID, int score)
    {
        //add to last 20 scores array
        for (int i = 19; i > 0; i--)
        {
            lastTwentyScores[repID][i] = lastTwentyScores[repID][i-1];
        }
        lastTwentyScores[repID][0] = score;
        //add to scores array
        this.scores[repID][score-1] += 1;
        checkAverage(repID);
        containsSomething = true;    
    }

    /**
     * Check to see if the rep average score has dipped below 2.5. Print message if average drops to 
     * below 2.5 or there are no recent scores to calculate.
     * @param repID the id of the rep
     * 
     */
    public void checkAverage(int repID)
    {
        getAverage(repID);
        if (runningAverage < 2.5 && checkRep == true && getAmountInArray(repID) >= 20) 
        {
            System.out.println("Rep " + repID + "'s running average has dropped to " + this.runningAverage);
            checkRep = false;
        }
        if (runningAverage < 2.5 && checkRep == true && getAmountInArray(repID)< 20) 
        {
            System.out.println("Rep " + repID + " does not have enough scores for an average");
        }
    }

    /**
     * Check to see how many scores are in the reps last 20 scores array 
     * @param repID the id of the rep
     * @return int the amount of scores in last 20 scores array
     */
    public int getAmountInArray(int repID)
    {
        int count = 0;
        for (int i = 0; i < 20; i++)
        {
            if (lastTwentyScores[repID][i] != 0)
            {
                count += 1;
            }
        }
        return count;
    }

    /**
     * Check to see if the rep is in good standing (equal to or above 2.5 rating)
     * @param repID the id of the rep
     * @return true if rep's score is greater than or equal to 2.5
     *
    public boolean checkRep(int repID)
    {
        return (runningAverage >= 2.5 || lastTwentyScores[repID][0] == 0 || );
    } 

    /**
     * Get the average of the rep
     * @param repID the id of the rep
     * @return double the average of the rep
     */
    public double getAverage(int repID)
    {
        int currentAverage = 0;
        for (int i = 0; i < 20; i++)
        {
            currentAverage += lastTwentyScores[repID][i];
        }
        int numerator = currentAverage;
        int denominator = getAmountInArray(repID);
        runningAverage = (double) numerator/denominator;  
        if (runningAverage >= 2.5 || lastTwentyScores[repID][0] == 0)
        {
            checkRep = true;
        }
        return runningAverage;
    }

    /**
     *Get the reps cumulative score
     * @param repID the id of the rep
     * @return an array of length this.numberOfPossibleScoreswith the current score totals for the rep
     */
    public int[] getCumulativeScoreForRep(int repID)
    {
        return Arrays.copyOf(this.scores[repID],this.scores[repID].length);
    }

    /**
     *Get the reps cumulative score and average
     * @param repID the id of the rep
     * @return an array that contains both an array of cumulative score and also rep's average
     */
    public int[][] getCumulativeScoreForRepAndAverage(int repID)
    {
        int[][] bothScores = new int[2][this.scores[repID].length];
        int[] cumulative = getCumulativeScoreForRep(repID);
        bothScores[0] = cumulative;
        double average = getAverage(repID);
        bothScores[1][0] = (int) average;
        return bothScores;
    }

    /**
     * Reset the scores and average of a single rep
     * @param repID
     * 
     */
    public void resetSingleAverageAndCumulativeScores(int repID)
    {
        for (int i = 0; i < lastTwentyScores[repID].length; i++)
        {
            lastTwentyScores[repID][i] = 0;
        }
        for (int i = 0; i < scores[repID].length; i++)
        {
            scores[repID][i] = 0;
        }
    }

    /**
     * Reset the scores and average of all reps
     * 
     */
    public void resetAllAverageAndCumulativeScores()
    {
        for (int i = 0; i < repQuantity - 1; i++)
        {
            resetSingleAverageAndCumulativeScores(i);
        }
    }

}

