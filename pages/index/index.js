// index.js
// 获取应用实例
const app = getApp()

Page({
  data: {
    current: 'index',
    announcement: 'LSS 图书共享系统，分享你闲置的书籍让你的书流动起来吧！',
    scrollPoster1: 'https://pic1.zhimg.com/v2-e726985f44869b525e50f826caf042c4_1440w.jpg?source=172ae18b',
    scrollPoster2: 'https://pic1.zhimg.com/v2-f1c786879e229d8b30bc8f2f7bb723b6_1440w.jpg?source=172ae18b',
    scrollPoster3: 'https://i0.hdslb.com/bfs/article/c4ab3145e4a5c875ad329810106a8d001d0c658b.jpg@progressive.webp',
    openId: '',
    resscode: ''
  },
  handleChange ({ detail }) {
    this.setData({
        current: detail.key
    });
    wx.redirectTo({
      url: '/pages/'+detail.key+'/index',
    });
  },
  onLoad(){
    
  },
  navigateToBook(event){
      let navigateURL = event.currentTarget.dataset.url;
      wx.navigateTo({
        url: '/pages/book/' + navigateURL +  '/index',
      })
  }
})
