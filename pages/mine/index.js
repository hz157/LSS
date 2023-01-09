// pages/my/index.js
import server from '../../config/server.js'

Page({

  data: {
    current: 'mine',
    username: '',
    uid: '',
    avatar: '',
    integral: ''
  },
  handleChange ({ detail }) {
    this.setData({
        current: detail.key
    });
    wx.redirectTo({
      url: '/pages/'+detail.key+'/index',
    })
  },
  onShow(){
    wx.request({
      url: server.host + '/userInfo/getInfo',
      method: 'POST',
      header:{
        "token": server.token
      },
      success: (result) => {
        var data = result.data['data']
        console.log(data)
        console.log(data['avatar'])
        this.setData({
          username: data['username'],
          uid: data['id'],
          integral: data['integral'],
          avatar: server.host + "/file/avatar?fileName=" + data['avatar'],
      });
        console.log(this.data.avatar)
        
      }
    })

  },
  AvatarEdit () {
    let path = '';
    wx.chooseImage({
      count:1, //一次上传的数量
      sizeType: ['original', 'compressed'],
      sourceType: ['album', 'camera'], // album 从相册选图，camera 使用相机，默认二者都有
      success: res=>{
        var tempFilePaths = res.tempFilePaths
        console.log(tempFilePaths)
        wx.uploadFile({
          url: server.host + "/file/fileUpload",
          filePath: tempFilePaths[0],
          name: 'fileName',
          header: {
            "Content-Type": "multipart/form-data",
            'token': server.token,
          },
          success:(res)=>{
            var data = JSON.parse(res.data)
            path = data['data']
            wx.request({
              url: server.host + "/userInfo/editAvatar?path="+path,
              header: {token: server.token},
              method: 'POST',
              success: (res)=>{
                var data = res.data
                console.log(data)
                wx.showToast({
                  title: '修改成功',
                  icon: 'success',
                  duration: 2000
                })
                this.setData({
                  avatar: server.host + "/file/avatar?fileName=" + path,
                })
                console.log(this.data.avatar)
              }
            })
          },
          fail:err=>console.log(err)
        })
      }
    })
},
loginout(){
  server.token = ''
}
})