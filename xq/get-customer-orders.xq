xquery version "1.0";
    for $i in //customer
    let $name := concat($i/@FirstName, " ", $i/@LastName)
    order by $i/@LastName
    return
    <Customer Name="{$name}">
    {
        $i/order
    }
    </Customer>