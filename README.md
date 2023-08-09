# Twitter-Bot-Checker
A Twitter bot developed with Twitter4j and Botometer to determine a confidence interval surrounding the proportion of active Twitter accounts that are automated.

Twitter Account: Bot Checker (@Games_Bot1)

<img width="596" alt="Screen Shot 2023-06-28 at 12 21 44 AM" src="https://github.com/NickCoding22/Twitter-Bot-Checker/assets/105902020/60683ba7-ddde-4885-92d2-fb866f85692e">

Number of Samples: 168

Total Amount of Accounts Sampled: 13440

Total Amount of Accounts Deemed to be Bots: 1351

Total Bot Proportion: 0.100520834

Confidence Interval: "I am 95% confident that the interval of 0.0954 to 0.1056 contains the true proportion (P) of active Twitter acounts that are bots."

The Java-based program was developed as a final project for AP Stats in May of 2022 and it aimed to estimate the total proportion of active Twitter acounts that were automated. For every hour of every day for 7 days the program would open a Twitter data stream, listening for new tweets, and--of the 80 tweets it would collect in that hour--determine if the associated account was a bot and record the data to a txt file. The data collected was the Botometer estimates, the users' ID's and @'s, and the sample proportion and total proportion. After 80 accounts' data were collected, a tweet would be made via the account "Bot Checker" (@Games_Bot1), including the amount of accounts sampled, total amount of accounts that were deemed to be bots, the proporiton of bots in the sample, and the total proportion of bots in the study. In all, 13,440 accounts were sampled and a 100% was earned on the project. 

Setup requires access and installation of the Twitter API and Botometer API endpoints, both of which have undergone changes in the time since the project's completion. Due to new Twitter API standards, the bot will no longer be functional in the coming months, and would only work with elevated API access to Twitter and (unlikely) continued updates from the Botometer python SDK. The program was run on a Raspberry Pi 4.
