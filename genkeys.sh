cd src/main/resources &&
openssl req -newkey rsa:2048 -new -nodes -keyout privatekey.pem -out csr.pem &&
openssl rsa -in privatekey.pem -pubout > publickey.pem && cd ../../../ &&
echo "Keys generated successfully"