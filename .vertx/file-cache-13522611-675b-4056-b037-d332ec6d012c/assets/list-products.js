function load() {
    $("#content").children().remove();
    $.getJSON("/api/getAllProducts", function (data) {
        console.log(data)
        $.each(data, function (key, val) {
            $("<tr><td>" + val.name + "</td><td>" + val.label + "</td>" +
                    "</tr>").appendTo("#content");
        });
    });
}