package io.samancore.common.page;


import io.samancore.common.model.PageData;
import io.smallrye.mutiny.Uni;

import jakarta.ws.rs.core.MultivaluedMap;
import java.util.List;
import java.util.function.Function;

import static io.samancore.common.page.PageParamConstant.*;

public class PageUtil {

    private PageUtil() {
    }

    public static PageRequest getPage(MultivaluedMap<String, String> queryParameters) {
        String pageSort = queryParameters.getFirst(SORT);
        String pageOrder = queryParameters.getFirst(ORDER);
        var pageNumber = Integer.parseInt(queryParameters.getFirst(PAGE));
        var pageLimit = Integer.parseInt(queryParameters.getFirst(LIMIT));

        return PageRequest.newBuilder()
                .setPage(pageNumber)
                .setLimit(pageLimit)
                .setSort(pageSort)
                .setOrder(pageOrder)
                .build();
    }

    public static <ENTITY> PageData<ENTITY> toPageEntity(List<ENTITY> list, long total) {
        return PageData.<ENTITY>newBuilder()
                .setData(list)
                .setCount(total)
                .build();
    }

    public static <ENTITY, MODEL> PageData<MODEL> toPageModel(PageData<ENTITY> model, Function<? super ENTITY, MODEL> mapper) {
        var modelList = model.getData().stream()
                .map(mapper)
                .toList();
        return PageData.<MODEL>newBuilder()
                .setData(modelList)
                .setCount(model.getCount())
                .build();
    }

    public static <ENTITY> Uni<PageData<ENTITY>> combineToPageData(Uni<List<ENTITY>> list, Uni<Long> total) {
        return Uni.combine().all().unis(list, total).asTuple()
                .onItem().transform(tuple -> {
                    var anyList = tuple.getItem1();
                    return PageData.<ENTITY>newBuilder()
                            .setData(anyList)
                            .setCount(tuple.getItem2())
                            .build();
                });
    }
}
