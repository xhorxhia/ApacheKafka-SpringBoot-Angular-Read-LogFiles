import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';

export class WebSocketAPI {
    webSocketEndPoint: string = 'http://localhost:8081/ws';  // endPoint created in spring consumer
    topic: string = "/topic/group";
    stompClient: any;
    
    listTopic1:any = [

        // {
        //     "message":""
        // },
    ]
    listTopic2:any = [

        // {
        //     "message":""
        // },
    ]
    actualTopic: string = "topic1"
    _connect() {
        console.log("Initialize WebSocket Connection");
        let ws = new SockJS(this.webSocketEndPoint);
        this.stompClient = Stomp.over(ws);
        const _this = this;
        _this.stompClient.connect({}, function (frame) {
            _this.stompClient.subscribe(_this.topic, function (message) {
                _this.onMessageReceived(message);
                console.log("******:: "+ message);
                
            });
            //_this.stompClient.reconnect_delay = 2000;
        }, this.errorCallBack);
    };

    _disconnect() {
        if (this.stompClient !== null) {
            this.stompClient.disconnect();
        }
        console.log("Disconnected");
    }

    // on error, schedule a reconnection attempt
    errorCallBack(error) {
        console.log("errorCallBack -> " + error)
        setTimeout(() => {
            this._connect();
        }, 5000);
    }

 /**
  * Send message to sever via web socket
  * @param {*} message 
  */
    // _send(message) {
    //     console.log("calling logout api via web socket");
    //     console.log("----",message);
        
    //     this.stompClient.send("/app/sendMessage", {}); //Json.Stringify(message)
    // }

    onMessageReceived(message) {
        console.log("--: TOPIC SELECETED: "+ this.actualTopic);
        
        console.log("Message Recieved from Kafka :: ", message.body);
        //if(this.actualTopic == "topic1")
        //{
            this.listTopic1.push(message.body);
        //}
        // else
        // {
        //     this.listTopic2.push(message.body);
        // }
        //this.appComponent.handleMessage(JSON.stringify(message.body));
    }
}