function load() {
    $("#content").children().remove();
    $.getJSON("/api/getAllProducts", function (data) {
        $.each(data, function (key, val) {
            $("<tr><td>" + val.id + "</td><td>" + val.name + "</td><td>" + val.label + "</td>" +
                    "</tr>").appendTo("#content");
        });
    });
}