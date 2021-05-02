
Get OHLC data from Kraken please checkout URL: https://api.kraken.com/0/public/OHLC

Input:
pair = asset pair to get OHLC data for
interval = time frame interval in minutes (optional):1 (default), 5, 15, 30, 60, 240, 1440, 10080, 21600
since = return committed OHLC data since given id (optional.  exclusive)

Result: array of pair name and OHLC data
<pair_name> = pair name
array of array entries(<time>, <open>, <high>, <low>, <close>, <vwap>, <volume>, <count>)
last = id to be used as since when polling for new, committed OHLC data
Note: the last entry in the OHLC array is for the current, not-yet-committed frame and will always be present,
regardless of the value of "since".

Warnings:
-Check and change application.properties as needs.
-Check URL pair name and interval in methods as below.
    String dailyURL = "https://api.kraken.com/0/public/OHLC?pair=XBTUSD&interval=1440";
-Check database pathway in getLastDateOfJSONFilesName() as below.
     File dataFilesInPathName = new File("C:\\Users\\IdeaProjects\\KrakenExchangeDataFeeder");
     dataFilesInPathName points that all database will be saved as json file in local.
