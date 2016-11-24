#!/usr/bin/python

import sys
import boto3

client = boto3.client('ses', region_name='us-west-2')
response = client.send_email(
    Source='Jenkins Lab <craigjk@cox.net>',
    Destination={
        'ToAddresses' : [
            'craigjk@cox.net'
        ]
    },
    Message={
        'Subject' : {
            'Data' : sys.argv[1] + ' ' + sys.argv[2]
        },
        'Body' : {
            'Text' : {
                'Data' : ' '
            }
        }
    }
)
