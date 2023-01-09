// pages/book/returnbook/index.js
import server from '../../../config/server.js'
Page({

  /**
   * 页面的初始数据
   */
  data: {
    record: [],
    info: []
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
    // 网络请求
    wx.request({
      url: server.host + '/stock/borrowRecord?type=live',
      method: "POST",
      header:{"token":server.token},
      success: (result) => {
        var data = result.data['data']
        console.log(data)
        // 遍历
        for(var i=0;i<data.length;i++){
          let tmp =  data[i]
          // 网络请求
            wx.request({
              url: server.host + '/stock/stockGetInfo?id=' + tmp['id'],
              method: "POST",
              header:{"token":server.token},
              success: (res) => {
                this.setData({
                  info: this.data.info.concat(res.data['data']),
                  record: this.data.record.concat(tmp)
                })
              }
            })
        }
        // this.setData({
        //   record: data['data']
        // })
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
  tapCard(event){
    var bid = event.currentTarget.id;
    wx.showModal({
      title: '提示',
      content: '确认归还书籍',
      complete: (res) => {
        if (res.confirm) {
          // 网络请求
          wx.request({
            url: server.host + "/stock/returnBook?bid=" + bid,
            method: "POST",
            header: {"token" : server.token},
            success: (result) => {
                var data = result.data
                if(data['code'] == "200" && data['msg'].search("成功")!=-1){
                  // 提示框
                  wx.showModal({
                    title: '提示',
                    content: '归还成功',
                    complete: (res) => {
                      // 页面跳转
                      wx.navigateTo({
                        url: '/pages/index/index',
                      })
                    }
                  })
                }else{
                  // 自动销毁的提示框
                  wx.showToast({
                    title: data['msg'],
                    icon: 'error',
                    duration: 2000
                  })
                }
            }
          })
        }
      }
    })
  }
})