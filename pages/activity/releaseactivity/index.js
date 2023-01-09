// pages/activity/releaseactivity/index.js
import server from '../../../config/server.js';
var dateTimePicker = require('../../../utils/dateTimePicker.js');
Page({

  /**
   * 页面的初始数据
   */
  data: {
      activity:{
        'name':'',
        'address':'',
        'peopleLimit':'',
        'registrationDate':'',
        'deadline':'',
        'startDate':''
      },
      date: '2018-10-01',
      time: '12:00',
      dateTimeArray: null,
      dateTime: null,
      dateTimeArray1: null,
      dateTime1: null,
      startYear: 2000,
      endYear: 2050,
      registrationDate:'',
      deadline:'',
      startDate:''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    // 获取完整的年月日 时分秒，以及默认显示的数组
    var obj = dateTimePicker.dateTimePicker(this.data.startYear, this.data.endYear);
    var obj1 = dateTimePicker.dateTimePicker(this.data.startYear, this.data.endYear);
    // 精确到分的处理，将数组的秒去掉
    var lastArray = obj1.dateTimeArray.pop();
    var lastTime = obj1.dateTime.pop();
    this.setData({
      dateTime: obj.dateTime,
      dateTimeArray: obj.dateTimeArray,
      dateTimeArray1: obj1.dateTimeArray,
      dateTime1: obj1.dateTime
    });
  },
  hangeDate(e){
    this.setData({ date:e.detail.value});
  },
  changeTime(e){
    this.setData({ time: e.detail.value });
  },
  changeDateTime(e){
    this.setData({ dateTime: e.detail.value });
  },
  changeDateTime1(e) {
    this.setData({ dateTime1: e.detail.value });
  },
  changeDateTimeColumn(e){
    var arr = this.data.dateTime, dateArr = this.data.dateTimeArray;

    arr[e.detail.column] = e.detail.value;
    dateArr[2] = dateTimePicker.getMonthDay(dateArr[0][arr[0]], dateArr[1][arr[1]]);

    this.setData({
      dateTimeArray: dateArr,
      dateTime: arr
    });
  },
  changeDateTimeColumn1(e) {
    var arr = this.data.dateTime1, dateArr = this.data.dateTimeArray1;

    arr[e.detail.column] = e.detail.value;
    dateArr[2] = dateTimePicker.getMonthDay(dateArr[0][arr[0]], dateArr[1][arr[1]]);

    this.setData({
      dateTimeArray1: dateArr,
      dateTime1: arr
    });
  },
  changeDateTime(event){
      console.log(event)
      var id = event.currentTarget.id
      var value = event.detail.value
      this.setData({
        [id]: "20" + value[0] + "-" + (value[1]+1) + "-" + (value[2]+1) + " " + value[3] + ":" + value[4] + ":" + value[5],
      })
  },
  inputMsg(event){
    console.log(event);
    let type = event.target.id;
    this.setData({
      [type]: event.detail.detail.value
    })
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
  release(){
    wx.request({
      url: server.host + '/activity/release',
      method:"POST",
      header:{"token":server.token},
      data: this.data.activity,
      success: (res) => {
        var data = res.data
        if(data['code'] == "200" && data['msg'].search("成功") != -1){
          wx.showModal({
            title: '提示',
            content: '发布成功',
            complete: (res) => {
              wx.navigateTo({
                url: '/pages/activity/index',
              })
            }
          })
        }else{
          wx.showToast({
            title: '发布失败',
            icon:'error',
            duration:2000
          })
        }
      }
    })
  }
})