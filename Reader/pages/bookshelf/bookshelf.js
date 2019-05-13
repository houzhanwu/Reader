// pages/bookshelf/bookshelf.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    bookshelf:[],
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
  },
 getBookshelf: function(){
   bookshelf = wx.getStorageSync("bookshelf")
   this.setData({
     bookshelf : bookshelf
   })
 }
})