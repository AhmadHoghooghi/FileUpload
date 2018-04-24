import requests
import os
from os import path

#pip install requests-toolbelt
from requests_toolbelt.streaming_iterator import StreamingIterator
import os


# To Upload a file Just Change This Line:
filePath = "C:\\Uploads\\Source\\4M.dat"
# This is URL of file-upload-webservice
serverURL="http://localhost:8080/upload-webservice/bigfileupload"

#retrieve fileName from path, this will be sent as a header 
fileName = path.split(filePath)[1]
size =os.path.getsize(filePath);
headers = {"File-Name": fileName, "Content-Type": "application/octet-stream"}
resp = requests.post(serverURL, headers=headers, data=StreamingIterator(size, open(filePath, 'r+b',1024)))

#print result
print resp.status_code
print resp.text