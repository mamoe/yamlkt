package net.mamoe.yamlkt.decoder

import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import net.mamoe.yamlkt.Yaml
import net.mamoe.yamlkt.YamlDecodingException
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertFailsWith


internal class BlockSequenceFailTest {


    @Test
    fun `nested descriptor block sequence3`() {
        assertFailsWith<YamlDecodingException> {
            Yaml.default.decodeFromString(
                ListSerializer(ListSerializer(String.serializer())),
                """
        - pp
        - sss
        -
          ss
        """
            )
        }
    }

    @Test
    fun `nested descriptor block sequence4`() {
        val list = Yaml.default.decodeFromString(
            ListSerializer(ListSerializer(String.serializer())),
            """
    - 
      - sss
      - ss
    - 
      - sss
      - ss
    
    """
        )
        assertEquals(listOf(listOf("sss", "ss"), listOf("sss", "ss")), list)
    }

    @Test
    fun `nested descriptor block sequence5`() {
        assertEquals(
            listOf(listOf("sss - ss")),
            Yaml.default.decodeFromString(
                ListSerializer(ListSerializer(String.serializer())),
                """
        - 
          - sss
           - ss
        
        """
            )
        )
    }

    @Test
    fun `nested descriptor block sequence6`() {
        assertFails {
            Yaml.default.decodeFromString(
                ListSerializer(ListSerializer(String.serializer())),
                """
        - 
          - sss
         - ss
        
        """
            )
        }
    }

    @Test
    fun `nested descriptor block sequence7`() {
        assertEquals(
            listOf(listOf("sss - ss")),
            Yaml.default.decodeFromString(
                ListSerializer(ListSerializer(String.serializer())),
                """
        - 
          - sss
            - ss
        
        """
            )
        )
    }

}