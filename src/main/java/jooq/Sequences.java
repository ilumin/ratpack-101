/**
 * This class is generated by jOOQ
 */
package jooq;


import javax.annotation.Generated;

import org.jooq.Sequence;
import org.jooq.impl.SequenceImpl;


/**
 * Convenience access to all sequences in PUBLIC
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Sequences {

    /**
     * The sequence <code>PUBLIC.SYSTEM_SEQUENCE_65565CE4_417E_4B1D_A294_34CA6F0CCECB</code>
     */
    public static final Sequence<Long> SYSTEM_SEQUENCE_65565CE4_417E_4B1D_A294_34CA6F0CCECB = new SequenceImpl<Long>("SYSTEM_SEQUENCE_65565CE4_417E_4B1D_A294_34CA6F0CCECB", Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT);
}