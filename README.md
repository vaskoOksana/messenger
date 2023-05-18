Testing instruction
1. Start kafka and redis locally in docker with command
   `docker compose up`.
2. Run `consumer` MS and `publisher` MS locally.
3. In the browser enter `localhost:8084` and enter credetilas from application.properties 

<img width="1123" alt="Screenshot 2023-05-18 at 15 46 20" src="https://github.com/vaskoOksana/messenger/assets/132194606/587ab127-3896-4f3c-a354-c9dd03e7d0d9">

4. Make request.

```
curl -L -X POST 'http://localhost:8081/message' \
-H 'Content-Type: application/json' \
--data-raw '{
    "text": "spring",
    "receiver": "oksana"
}'

```

<img width="953" alt="Screenshot 2023-05-18 at 15 48 17" src="https://github.com/vaskoOksana/messenger/assets/132194606/51cca73b-dc72-4328-afce-904ad19e7234">

Expected result - in browser message text has appeared.

<img width="1012" alt="Screenshot 2023-05-18 at 15 51 17" src="https://github.com/vaskoOksana/messenger/assets/132194606/8c76eabf-c719-443e-9469-77c74df73f0b">


5. Close the tab in browser and make request again with changed value of `text` field.

Expected result - message was saved in redis.

<img width="1111" alt="Screenshot 2023-05-18 at 16 01 48" src="https://github.com/vaskoOksana/messenger/assets/132194606/1e7b3609-0d45-400b-b350-d3833e76680a">


Open the browser and enter again `localhost:8084`.

Expected result - last 2 messages have appeared.
 
<img width="664" alt="Screenshot 2023-05-18 at 15 54 48" src="https://github.com/vaskoOksana/messenger/assets/132194606/8ed642c1-a2c7-4178-aeaa-e4d4442c4c4f">



Additional info.

You can test kafka locally.
- Create a topic:

docker exec broker \
kafka-topics --bootstrap-server broker:9092 \
             --create \
             --topic direct

- Write message to a topic:

docker exec --interactive --tty broker \
kafka-console-producer --bootstrap-server broker:9092 \
                       --topic direct

Type in some lines of text. Each line is a new message.
hello world!
When you’ve finished, enter Ctrl-C to return to your command prompt.

- Read messages from the topic:

docker exec --interactive --tty broker \
kafka-console-consumer --bootstrap-server broker:9092 \
                       --topic direct \
                       --from-beginning


- Stop kafka:
docker compose down
