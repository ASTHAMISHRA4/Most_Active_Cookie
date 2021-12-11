Most Active Cookie

Given a cookie log file in the following format:

cookie,timestamp
AtY0laUfhglK3lC7,2018-12-09T14:19:00+00:00
SAZuXPGUrfbcn5UA,2018-12-09T10:13:00+00:00
5UAVanZf6UtGyKVS,2018-12-09T07:25:00+00:00
AtY0laUfhglK3lC7,2018-12-09T06:19:00+00:00
SAZuXPGUrfbcn5UA,2018-12-08T22:03:00+00:00
4sMM2LxV07bPJzwf,2018-12-08T21:30:00+00:00
fbcn5UAVanZf6UtG,2018-12-08T09:30:00+00:00
4sMM2LxV07bPJzwf,2018-12-07T23:30:00+00:00

Write a command line program in your preferred language to process the log file and return the most active cookie for specified day. The example below shows how we'll execute your program.

Command:

$ ./most_active_cookie cookie_log.csv -d 2018-12-09

Output:

AtY0laUfhglK3lC7


We define the most active cookie as one seen in the log the most times during a given day.

Assumptions:
•	If multiple cookies meet that criteria, please return all of them on separate lines.

$ ./most_active_cookie cookie_log.csv -d 2018-12-08

SAZuXPGUrfbcn5UA
4sMM2LxV07bPJzwf
fbcn5UAVanZf6UtG
