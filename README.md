Testing instruction
1. Start kafka and redis in docker:
   docker compose up -d


Test kafka locally:
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

2. Run consumer MS and publiasher MS locally
3. In browser enter localhost:8084 -> enter credetilas from application-properties
4. make request

```
curl -L -X POST 'http://localhost:8081/message' \
-H 'Content-Type: application/json' \
--data-raw '{
    "text": "spring",
    "receiver": "oksana"
}'

```

Expected result: in browser you can see the message text
5. Close the tab in browser and make request again(change the value of "text" field).
Expected result: message was saved in redis.
Open browser and localhost:8084. Expected result - last message appairs 