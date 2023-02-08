#!/bin/bash
cat $1 | sed -n '/-----BEGIN PRIVATE KEY-----/,/-----END PRIVATE KEY-----/{//b;p}' | tr -d '\n'
echo -e
