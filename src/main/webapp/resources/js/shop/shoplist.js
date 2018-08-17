$(function() {
    getlist();
    function getlist(e) {
        $.ajax({
            url : "/o2omall/shopadmin/getshoplist",
            type : "get",
            dataType : "json",
            success : function(data) {
                if (data.success) {
                    handleList(data.shopList);
                    handleUser(data.user);
                }
            }
        });
    }
    function handleUser(data) {
        $('#user-name').text(data.name);
    }

    function handleList(data) {
        var html = '';
        data.map(function(item, index) {
            html += '<div class="row row-shop"><div class="col-40">'
                + item.shopName + '</div><div class="col-40">'
                + shopStatus(item.enableStatus)
                + '</div><div class="col-20">'
                + goShop(item.enableStatus, item.shopId) + '</div></div>';

        });
        $('.shop-wrap').html(html);
    }

    function shopStatus(status) {
        if (status == 0) {
            return 'In Checking';
        } else if (status == -1) {
            return 'Illegal Shop';
        } else if (status == 1) {
            return 'Pass';
        }
    }

    function goShop(status, id) {
        if (status == 1) {
            return '<a href="/o2omall/shopadmin/shopmanagement?shopId=' + id
                + '">Enter</a>';
        } else {
            return '';
        }
    }
});