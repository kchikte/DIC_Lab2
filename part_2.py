import tweepy
import csv #Import csv
auth = tweepy.auth.OAuthHandler('3ea7O690qIuq87jRja1M46xKv', 'lP4QPxaiYuCNkvhkJJ4jHnn7VgKBFSjW51gxGMV9ezo3DIHGpb')
auth.set_access_token('966826371950567425-2bmyxEQVhAd4XLWdpCJMgHo3D1ogNFp', 'L3EN4SAhQuz8lwtvYg4S2nmdZt5KBnrkym783vwbd7Xen')
api = tweepy.API(auth)
# Open/create a file to append data
csvFile = open('twitterData_gun_shooting_2.csv', 'a')
#Use csv writer
csvWriter = csv.writer(csvFile)
for tweet in tweepy.Cursor(api.search,q = "gun shooting",lang = "en", wait_on_rate_limit=True, wait_on_rate_limit_notify=True).items(10000):
    #print(tweet)
    # Write a row to the CSV file. I use encode UTF-8
    csvWriter.writerow([tweet.text.encode('utf-8')]),
    print(tweet.created_at, tweet.text)
csvFile.close()