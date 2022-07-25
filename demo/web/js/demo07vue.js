var app = new Vue({
    el: "#div_container",
    data: {
        fruits: [
            {name: "苹果", price: 2, amount: 10 },
            {name: "香蕉", price: 3, amount: 5 },
            {name: "西瓜", price: 20, amount: 1 },
            {name: "梨", price: 3, amount: 6 },
        ],
        form: {
            name: "苹果",
            price: 2,
            amount: 1,
        },
    },
    methods: {
        remove: function (index){
            this.fruits.splice(index, 1);

        },
        addFruit: function (){
            console.log(this.form);
            this.fruits.push(this.form);
        }
    }

})