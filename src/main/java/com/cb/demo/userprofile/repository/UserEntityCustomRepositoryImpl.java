package com.cb.demo.userprofile.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.couchbase.core.CouchbaseOperations;
import org.springframework.stereotype.Component;

import com.cb.demo.userprofile.model.UserEntity;
import com.cb.demo.userprofile.services.vo.SimpleUserVO;
import com.cb.demo.userprofile.services.vo.UserFoundImVO;
import com.couchbase.client.java.query.QueryOptions;
import com.couchbase.client.java.query.QueryScanConsistency;
import com.couchbase.client.java.search.SearchOptions;
import com.couchbase.client.java.search.SearchQuery;
import com.couchbase.client.java.search.queries.BooleanFieldQuery;
import com.couchbase.client.java.search.queries.ConjunctionQuery;
import com.couchbase.client.java.search.queries.DisjunctionQuery;
import com.couchbase.client.java.search.queries.MatchQuery;
import com.couchbase.client.java.search.result.SearchResult;
import com.couchbase.client.java.search.result.SearchRow;

@Component
public class UserEntityCustomRepositoryImpl  implements UserEntityCustomRepository{

    @Autowired
    private CouchbaseOperations couchbaseOperations ;

    @Override
    public List<SimpleUserVO> ftsListActiveUsers(String firstName, boolean enabled, String countryCode, Integer limit, Integer offset) {
        String fts= " and search(firstName,{\"should\":{" +
                "            \"disjuncts\": [" +
                // tag::fuzzy[]
                "            {\"field\":\"firstName\", \"match\": \""+firstName+"\"}," +
                "            {\"field\":\"firstName\", \"match\": \""+firstName+"\", \"fuzziness\":1}" +
                // end::fuzzy[]
                "                    ] }})" ;

        String query = "Select meta().id as id, username, tenantId, firstName, lastName from "
                + couchbaseOperations.getBucketName()
                +" where _class = '"+ UserEntity.class.getName()+"'"+fts+"  and enabled = "+enabled+" " +
                " and countryCode = '"+countryCode+"' order by firstName desc limit "+limit+ " offset "+offset;
        // "Not bounded" being the default, the query engine will grab and return what is in the index right now
   
        
        QueryOptions options=QueryOptions.queryOptions().scanConsistency(QueryScanConsistency.NOT_BOUNDED).adhoc(false);
       
        
      return   couchbaseOperations.getCouchbaseClientFactory().getCluster().query(query, options).rowsAs(SimpleUserVO.class);
        
        
    }


    public List<UserFoundImVO> scoreListActiveUsers(String firstName, boolean enabled, String countryCode, Integer limit, Integer skip ) {

        String indexName = "user_index_FTS";
        // tag::fuzzy[]
        MatchQuery firstNameFuzzy = SearchQuery.match(firstName).fuzziness(1).field("firstName");
        MatchQuery firstNameSimple = SearchQuery.match(firstName).field("firstName");
        DisjunctionQuery nameQuery = SearchQuery.disjuncts(firstNameSimple, firstNameFuzzy);
        // end::fuzzy[]
        // tag::filter[]
        BooleanFieldQuery isEnabled = SearchQuery.booleanField(enabled).field("enabled");
        MatchQuery countryFilter = SearchQuery.match(countryCode).field("countryCode");
        // end::filter[]
        // tag::conj[]
        ConjunctionQuery conj = SearchQuery.conjuncts(nameQuery, isEnabled, countryFilter);
        // end::conj[]
        // tag::result[]
        SearchOptions option = SearchOptions.searchOptions().fields("id", "tenantId", "firstName", "lastName", "userName" )
                .skip(skip)
                .limit(limit);
        SearchResult result = couchbaseOperations.getCouchbaseClientFactory().getCluster().searchQuery(indexName,nameQuery,option);
                       


        List<UserFoundImVO> simpleUsers = new ArrayList<>();
        if (result != null && result.metaData().errors().isEmpty()) {

        	List<SearchRow> results= result.rows();
           
            
            for (SearchRow row : results) {
				
            	row.fieldsAs(UserFoundImVO.class);
            	
            	
//            	simpleUsers.add(new UserFoundImVO(
//            			row.id(),row.field
//            			new Long(fields.get("tenantId")),
//            			fields.get("firstName"),
//            			fields.get("lastName"),
//            			row.fragments()));
			}
          
            
        }

        return simpleUsers;
        // end::result[]
    }


    public List<SimpleUserVO> listActiveUsers(String firstName,  boolean enabled, String countryCode,  Integer limit, Integer offset ) {

        String query = "Select meta().id as id, username, tenantId, firstName, lastName from "
                +couchbaseOperations.getBucketName()
                +" where _class = '"+UserEntity.class.getName()+"' and firstName like '"+firstName+"' and enabled = "+enabled+" " +
                " and countryCode = '"+countryCode+"' order by firstName desc limit "+limit+ " offset "+offset;
        // REQUEST_PLUS when a query is performed — wait until all of the indexes used have caught up to their highest internal sequence numbers.
        QueryOptions options=QueryOptions.queryOptions().scanConsistency(QueryScanConsistency.REQUEST_PLUS).adhoc(false);
      
        return   couchbaseOperations.getCouchbaseClientFactory().getCluster().query(query, options).rowsAs(SimpleUserVO.class);
    }
}
