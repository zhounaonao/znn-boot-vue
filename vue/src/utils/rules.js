//手机验证
import request from "@/utils/request";

export function checkMobile (rule, value, callback){
    if(!value){
        callback();
    }
    let regFormat = /^[1][3578][0-9]{9}$/; //正确手机号
      if (!(regFormat.test(value))) {
        callback(new Error('请输入正确手机号'));
      } else {
        callback();
      }
  };
  //手机验证，不能为空
  export function checkMobileNotNull (rule, value, callback){
    if (!value) {
        return callback(new Error('手机号不能为空'));
    }
    let regFormat = /^[1][3578][0-9]{9}$/; //正确手机号
      if (!(regFormat.test(value))) {
        callback(new Error('请输入正确手机号'));
      } else {
        callback();
      }
  };

  //数字验证
  export function checkNumber (rule, value, callback){
    if (!value) {
      return callback(new Error('不能为空'));
    }
      if (!Number.isInteger(value)) {
        callback(new Error('请输入数字值'));
      } else {
        callback();
      }
  };

  //邮箱
  export function checkEmail(rule, value, callback){
    let mal = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
    if (!value) {
      return callback(new Error('不能为空'));
    }
    if(!(mal.test(value))) {
      callback(new Error('请输入正确邮箱'));
    }else{
      callback();
    }
  };

  // 类似金钱,首位不为0,最多2位小数
  export function checkNumPot2(rule, value, callback) {
    const reg = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/
    if (!value) {
      return callback(new Error('请填写数字'))
    } else if (!reg.test(value)) {
      return callback(new Error('请填写数字,最多2位小数'))
    } else {
      callback()
    }
  }
   
  // 身份证
  export function checkIdNum(rule, value, callback) {
    const reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/
    if (!value) {
      return callback(new Error('证件号码不能为空'))
    } else if (!reg.test(value)) {
      return callback(new Error('证件号码不正确'))
    } else {
      callback()
    }
  }
   
  // 整数
  export function checkInterNum(rule, value, callback) {
    const reg = /^[0-9]*[1-9][0-9]*$/
    if (!value) {
      return callback(new Error('请填写整数'))
    } else if (!reg.test(value)) {
      return callback(new Error('请输入整数'))
    } else {
      callback()
    }
  }

// 用户名已存在
export function checkUserName(rule, value, callback) {
  console.log(value)
  if (!value) {
    return callback(new Error('请填写用户名'))
  } else{
    request.get("/register/" + value).then(res => {
      console.log(res)
      if (res.code === 200){
        callback()
      } else {
        return callback(new Error('用户名已存在'))
      }
    })
  }
}
   
  export default {
    mobile: [{ required: false,  validator: checkMobile, trigger: 'blur' }],
    mobileNotNull: [{ required: false,  validator: checkMobileNotNull, trigger: 'blur' }]
    
  }
