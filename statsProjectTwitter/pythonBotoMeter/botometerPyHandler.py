'''
Simple python script to run an account name through
Botometer to determine the probability the associated 
account is automated.
'''
import sys
import botometer
import keys as KEYS

theInput = str(sys.argv[1])

rapidapi_key = KEYS.RAPIDAPI_KEY
twitter_app_auth = KEYS.TWITTER_APP_AUTH
bom = botometer.Botometer(wait_on_ratelimit=True,
                          rapidapi_key=rapidapi_key,
                          **twitter_app_auth)

# Check a single account by screen name
result = bom.check_account(theInput)

convertedResult = str(result)
englishIndex = convertedResult.index('english')
universalIndex = convertedResult.index('universal')
endIndex = convertedResult.index('},')

output = "E" + convertedResult[englishIndex + 10:universalIndex - 3] + "U" + convertedResult[universalIndex + 12 :endIndex]

print(output, sys.argv)
