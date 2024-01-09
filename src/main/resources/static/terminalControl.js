function listTopics() {
    axiosClient({
      method: 'get',
      url: '/topic/list',
      withCredentials: true
    }).then(response => {
      console.log(response.data);
      if (!response.data.success) {
        alert("api error: " + response.data.errMsg);
        return;
      }

      initTopicView(response.data.data);
    }).catch (function (e) {
      console.log(e);
      alert(e);
    });
}

function initTopicView(topicList) {
    var topicTable = document.createElement("table");

    for (var i = 0; i < topicList.length; i++) {
        var topic = topicList[i];
        var topicRow = document.createElement("tr");
        topicTable.appendChild(topicRow);

        topicRow.style.width = "100px";
        topicRow.style.height = "100px";
        topicRow.style.marginLeft = "auto";
        topicRow.style.marginRight = "auto";
        topicRow.style.marginTop = "auto";
        topicRow.style.marginBottom = "auto";
        topicRow.textContent = topic.topic;

        var apiTable = document.createElement("table");
        topicRow.appendChild(apiTable);

        for (var i = 0; i < topic.topicApiDTOList.length; i++) {
            var api = topic.topicApiDTOList[i];
            var apiRow = document.createElement("tr");
            apiTable.appendChild(apiRow);

            var apiCell = document.createElement("td");
            apiCell.style.width = "100px";
            apiCell.style.height = "100px";
            apiCell.style.marginLeft = "auto";
            apiCell.style.marginRight = "auto";
            apiCell.style.marginTop = "auto";
            apiCell.style.marginBottom = "auto";
            apiCell.textContent = api.api;

            var paramsCell = document.createElement("td");
            paramsCell.style.width = "100px";
            paramsCell.style.height = "100px";
            paramsCell.style.marginLeft = "auto";
            paramsCell.style.marginRight = "auto";
            paramsCell.style.marginTop = "auto";
            paramsCell.style.marginBottom = "auto";
            paramsCell.textContent = api.params;

            apiRow.appendChild(apiCell);
            apiRow.appendChild(paramsCell);
        }
    }
    document.getElementById('topicList').appendChild(topicTable);
}

window.onload = function () {
    listTopics();
}