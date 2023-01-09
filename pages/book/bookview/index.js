// pages/book/bookview/index.js
import server from '../../../config/server.js'
Page({

  /**
   * 页面的初始数据
   */
  data: {
    totalPage: -1,
    totalData: -1,
    page: 1,
    bottonStatus: true,
    bottonContext: "正在加载数据",
    books: [
    ]
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
    let page = this.data.page
    wx.request({
      url: server.host + "/book/getPageInfo?page=" + page,
      method: 'POST',
      header:{
        "token": server.token
      },
      success: (result) => {
          var data = result.data['data']
          this.setData({
            totalPage: data['pages'],
            totalData: data['total'],
            page: page + 1,
            books: data.records
          })
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
    wx.request({
      url: server.host + "/book/getPageInfo?page=" + 1,
      method: 'POST',
      header:{
        "token": server.token
      },
      success: (result) => {
        var data = result.data['data']
        // var tmp = data['records']
        // for(var i=0;i<tmp.length;i++){
        //   let j = tmp[i]
        //   wx.request({
        //     url: server.host + "/stock/getStock?isbn=" + j['isbn'],
        //     method: 'POST',
        //     header:{"token": server.token},
        //     success: (res) => {
        //       j['stock'] = res.data.data.length
        //       tmp[i] = j
        //     }
        //   })
        // }
        this.setData({
          books: data.records,
          totalPage: data['pages'],
          totalData: data['total'],
          page: 1,
          bottonStatus: true,
          bottonContext: "正在加载数据"
        })
      }
    })
    
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom() {
    let page = this.data.page
    let totalPage = this.data.totalPage
    if(page!=totalPage){
      wx.request({
        url: server.host + "/book/getPageInfo?page=" + page,
        method: 'POST',
        header:{
          "token": server.token
        },
        success: (result) => {
            var data = result.data['data']
          //   var tmp = data['records']
          //   for(var i=0;i<tmp.length;i++){
          //     let j = tmp[i]
          //     wx.request({
          //       url: server.host + "/stock/getStock?isbn=" + j['isbn'],
          //       method: 'POST',
          //       header:{"token": server.token},
          //       success: (res) => {
          //         j['stock'] = res.data.data.length
          //         tmp[i] = j
          //       }
          //     })
          // }
            this.setData({
              books: this.data.books.concat(data.records),
              totalPage: data['pages'],
              totalData: data['total'],
              page: page + 1
            })
        }
      })
    }else{
      this.setData({
        bottonStatus: false,
        bottonContext: "已经到底啦，不能再下拉了",
      })
    }
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage() {

  },
  borrow(event){
    let isbn = event.currentTarget.dataset.bar_code;
    wx.navigateTo({
      url: '/pages/book/borrowbook/index?isbn=' + isbn
    });
  },
  scanCode(){
    var that = this;
    wx.scanCode({
      success(res) {
        console.log(res)
        wx.navigateTo({
          url: '/pages/book/borrowbook/index?isbn=' + res.result
        });
      }
    })
  },
})