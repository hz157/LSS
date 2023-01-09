// pages/book/releasebook/index.js
import server from '../../../config/server.js'
Page({

  /**
   * 页面的初始数据
   */
  data: {
      book:{},
      scanResult: '',
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
  inputMsg(event){
    console.log(event);
    let type = event.target.id;
    this.setData({
      [type]: event.detail.detail.value
    })
  },
  scanCode(){
    var that = this;
    wx.scanCode({
      success(res) {
        console.log(res)
        wx.request({
          url: server.host + '/book/getInfo?isbn=' + res.result,
          method: 'POST',
          header:{
            "token": server.token
          },
          success: (result) => {
            var data = result.data
            console.log(data)
            that.setData({
              book: data['data']
          });
          }
        })
      }
    })
    
  },
  release(){
    let isbn = this.data.book.isbn
    wx.request({
      url: server.host + "/stock/isexist?isbn=" + isbn,
      method: 'POST',
      header:{
        "token": server.token
      },
      success: (result) => {
        var data = result.data
        console.log(data)
        if(data['code']=="200" && data['msg'].search("找不到该书") != -1){
          wx.request({
            url: server.host + "/stock/sharebook?isbn=" + isbn,
            method: 'POST',
            header:{
              "token": server.token
            },
            success: (res) => {
              var addData = res.data
              if(addData['code']=="200" && addData['msg'].search("发布成功") != -1){
                wx.showModal({
                  title: '提示',
                  content: '藏书发布成功，可供他人借阅',
                  complete: (res) => {
                    if (res.confirm) {
                      wx.navigateTo({
                        url: '/pages/index/index',
                      })
                    }
                  }
                })
              }
            }
          })
        }else{
            wx.showModal({
              title: '提示',
              content: data['msg'],
              complete: (res) => {
                if (res.cancel) {
                  wx.navigateTo({
                    url: '/pages/index/index',
                  })
                }
            
                if (res.confirm) {
                  wx.request({
                    url: server.host + "/stock/sharebook?isbn=" + sendisbn,
                    method: 'POST',
                    header:{
                      "token": server.token
                    },
                    success: (res) => {
                      var addData = res.data
                      if(addData['code']=="200" && addData['msg'].search("发布成功") != -1){
                        wx.showModal({
                          title: '提示',
                          content: '藏书发布成功，可供他人借阅',
                          complete: (res) => {
                            if (res.confirm) {
                              wx.navigateTo({
                                url: '/pages/index/index',
                              })
                            }
                          }
                        })
                      }
                    }
                  })
                }
              }
            })
        }
      }
    })
  }
})