const axiosClient = axios.create();

axiosClient.interceptors.request.use(function(config) {
    const authorization=localStorage.getItem("Authorization");
    config.headers.Authorization = authorization ? authorization : '';
    return config;
}, function(error) {
    return Promise.reject(error);
});