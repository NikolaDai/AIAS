$(document).ready(function() {
    changePageAndSize();
});

function changePageAndSize() {
    $('#pageSizeSelect').change(function(evt) {
        var pathname = window.location.pathname;

        window.location.replace(pathname + "?pageSize=" + this.value + "&page=1");
    });
}