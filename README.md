# messenger
start kafka in docker:
docker compose up -d

Create a topic:

docker exec broker \
kafka-topics --bootstrap-server broker:9092 \
             --create \
             --topic direct

Write message to a topic:

docker exec --interactive --tty broker \
kafka-console-producer --bootstrap-server broker:9092 \
                       --topic direct

Type in some lines of text. Each line is a new message.

hello world!

When you’ve finished, enter Ctrl-C to return to your command prompt.

Read messages from the topic:

docker exec --interactive --tty broker \
kafka-console-consumer --bootstrap-server broker:9092 \
                       --topic direct \
                       --from-beginning


Stop kafka:

docker compose down