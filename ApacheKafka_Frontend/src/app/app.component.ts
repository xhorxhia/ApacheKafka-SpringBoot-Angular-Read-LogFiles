import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Component } from '@angular/core';
import { WebSocketAPI } from './WebSocketAPI';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent {
  title = 'client';
  content = "";
  source = "";
  
  finalList: any = []; 
  list: any = [];
  //sourceList: any = [];
  
  topicListDisplay = true;

  webSocketAPI: WebSocketAPI;
 



  constructor(private WebSocket: WebSocketAPI, private httpRequest: HttpClient) {
    this.webSocketAPI = WebSocket;
  }
  ngOnInit() {
    this.connect();
    this.list = this.webSocketAPI.listTopic1;
  }

  connect() {  // lidh me porten e webSocket
    this.webSocketAPI._connect();
  }

  disconnect() { 
    this.webSocketAPI._disconnect();
  }

// jep kohe per te marre kerkesen nga serveri
  resolveAfter2Seconds(x) {  
    return new Promise(resolve => {
      setTimeout(() => {
        resolve(x);
      }, 400);
    });
  }

  // funx qe therrsin secili buton pasi klikohen
  consumeTopic(topicname) {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',        
      })
    };
    this.topicListDisplay = true;  // shfaq cdo msg qe ka topic i zgjedhur
    this.finalList = [];

    const data = new FormData();
    data.append("topicname", topicname.target.name);
    // behet kerkesa dhe lidhet me porten e spring te consumer
    this.httpRequest.get<any>(`http://localhost:8081/test/changeTopic/${topicname.target.name}`).subscribe((res) => {
      console.log(res);
    });
    this.resolveAfter2Seconds(20).then(value => {
      this.list = this.webSocketAPI.listTopic1;
      console.log("Message From me: ", this.list[0]);

    })
    console.log("!!--- ", topicname.target.name);
  }

  // funxioni per kerkimin sipas filtrit
  searchFilter() {
    if(this.content != "" || this.source != ""){  // nese 1 nga fushat eshte bish, shfaq vetem sipas njeres 
      let param = new HttpParams();
       param = param.append("content", this.content);
       param = param.append("source", this.source);
      this.httpRequest.get<any>(`http://localhost:8081/test/search/`, {params: param}).subscribe((res) => {            
      this.finalList = res;
      console.log(this.finalList);

      this.topicListDisplay = false;  // fshi msg e meparshme nga topic dhe mbishkruaj keto te filtrit
    });

    }
  }

  // searchSource() {
  //   if(this.source != ""){
  //   this.httpRequest.get<any>(`http://localhost:8081/test/searchBySource/${this.source}`).subscribe((res) => {
  //     this.sourceList = res;
  //     console.log(this.sourceList);
  //     this.contentList=[];
  //   });
  // }
  // }

}
