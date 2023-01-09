// pages/activity/liveactivity/index.js
import server from '../../../config/server.js'
Page({

  /**
   * 页面的初始数据
   */
  data: {
    keyword: '',
    record: [],
    info: [],
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {

  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow() {
    wx.request({
      url: server.host + '/activity/getHistory?type=live',
      method:"POST",
      header:{"token":server.token},
      success: (result) => {
        var data = result.data
        console.log(data)
        this.setData({
          record: data['data']
        })
        for(var i=0;i<data['data'].length;i++){
          console.log(i)
          var tmp = data['data'][i]
          console.log(tmp)
            wx.request({
              url: server.host + '/activity/getRegisterNumber?id=' + tmp['id'],
              method:"POST",
              header:{"token":server.token},
              success: (res) => {
                this.setData({
                  info: this.data.info.concat(res.data['data'].length)
                })
                console.log(this.data.record)
              }
            })
        }
      }
    })
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh() {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom() {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage() {

  },
  inputText(event){
    console.log(event);
    let type = event.currentTarget.id;
    this.setData({
      [type]: event.detail.detail.value
    })
    wx.request({
      url: server.host + '/activity/keywordAct?keyword=' + this.data.keyword,
      method:"POST",
      header:{"token":server.token},
      success: (result) => {
        var data = result.data
        console.log(data)
        this.setData({
          record: data['data'],
          info: []
        })
        for(var i=0;i<data['data'].length;i++){
          console.log(i)
          var tmp = data['data'][i]
          console.log(tmp)
            wx.request({
              url: server.host + '/activity/getRegisterNumber?id=' + tmp['id'],
              method:"POST",
              header:{"token":server.token},
              success: (res) => {
                this.setData({
                  info: this.data.info.concat(res.data['data'].length)
                })
                console.log(this.data.record)
              }
            })
        }
      }
    })
  },
  tapCard(event){
    console.log(event)
    var id = event.currentTarget.id
    wx.showModal({
      title: '提示',
      content: '是否确认参加该活动？',
      complete: (res) => {
        if (res.cancel) {
          
        }
    
        if (res.confirm) {
          wx.request({
            url: server.host + '/activity/register?id='+id,
            method:"POST",
            header:{"token":server.token},
            success: (res)=>{
              var data = res.data
              if(data['code']=="200" && data['msg'].search("成功")!=-1){
                wx.request({
                  url: server.host + '/userInfo/addIntegral?type=1',
                  method: 'POST',
                  header:{"token": server.token},
                  success: (res) => {
                    console.log(res.data)
                  }
                })
                wx.showModal({
                  title: '提示',
                  content: '参加成功',
                  complete: (res) => {
                    if (res.confirm) {
                      wx.navigateTo({
                        url: '/pages/activity/index',
                      })
                    }
                  }
                })
              }else{
                wx.showToast({
                  title: '参加失败',
                  icon:"error",
                  duration:2000
                })
              }
            }
          })
        }
      }
    })
  }
})