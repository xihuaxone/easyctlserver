function userLogin() {
    var loginAccount = document.getElementById('loginAccount').value;
    var password = document.getElementById('password').value;
    data = {
        "loginAccount": loginAccount,
        "password": password
    }

    axiosClient({
      method: 'post',
      url: '/auth/login',
      data: data,
      withCredentials: true
    }).then(response => {
      console.log(response.data);
      if (!response.data.success) {
        document.getElementById('errMsg').innerHTML=response.data.errMsg;
        return;
      }
      if (response.headers.authorization) {
        localStorage.setItem('Authorization', response.headers.authorization);
        window.location.href="terminalControl.html";
      }
    }).catch (function (e) {
      console.log(e);
      document.getElementById('errMsg').innerHTML="未知异常：" + e.message;
    });
}
