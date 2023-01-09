// pages/activity/index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    current: 'activity',
    scrollPoster1: 'https://api.epoches.cn:9500/lss/file/avatar?fileName=1040da1f808c4ef0afd335ff6f013add.jpg',
    scrollPoster2: 'https://api.epoches.cn:9500/lss/file/avatar?fileName=7fa30e689afc48e38110dd6c10a32f22.jpg',
    scrollPoster3: 'https://api.epoches.cn:9500/lss/file/avatar?fileName=e08c67fe30a3456fbe20449874e6d074.jpg',
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
  handleChange ({ detail }) {
    this.setData({
        current: detail.key
    });
    wx.redirectTo({
      url: '/pages/'+detail.key+'/index',
    });
  },
  swiperTap(event){
    console.log(event)
    var id = event.currentTarget.id
    wx.navigateTo({
      url: '/pages/activity/detailactivity/index?id=' + id
    })
  }
})